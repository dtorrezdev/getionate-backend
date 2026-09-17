package com.dtorrez.main.modulos.ventas.services.venta.create;

import com.dtorrez.main.common.enums.EnumEvento;
import com.dtorrez.main.common.enums.EnumVenta;
import com.dtorrez.main.common.exceptions.BusinessRuleException;
import com.dtorrez.main.common.exceptions.DuplicateEntityException;
import com.dtorrez.main.common.exceptions.EntityNotFoundException;
import com.dtorrez.main.common.providers.CurrentUserProvider;
import com.dtorrez.main.modulos.evento.services.IEventoNotificacionService;
import com.dtorrez.main.modulos.inventario.services.movimiento.registrar.IRegistrarMovimientoService;
import com.dtorrez.main.modulos.ventas.controllers.dtos.pago.PagoRequest;
import com.dtorrez.main.modulos.ventas.controllers.dtos.pago.PagoResponse;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.*;
import com.dtorrez.main.modulos.ventas.mapper.VentaMapper;
import com.dtorrez.main.modulos.ventas.services.pago.IPagoService;
import com.micrium.bd.access.jpa.modulo.eventos.models.EventoNotificacion;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IStockRepository;
import com.micrium.bd.access.jpa.modulo.productos.models.ProductoPresentacion;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import com.micrium.bd.access.jpa.modulo.venta.repository.*;
import com.micrium.bd.access.jpa.modulo.venta.models.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CreateVentaServiceImpl implements ICreateVentaService {

    // Dominio del Modulo Venta
    private final IVentaRepository repository;
    private final IClienteRepository clienteRepository;
    private final IPagoService pagoService;

    // Dominio del Modulo Producto
    private IProductoPresentacionRepository productoRepository; // eliminar dependencia

    // Dominio del Modulo Inventario
    private final IRegistrarMovimientoService registrarMovimientoService;
    private final IStockRepository stockRepository;

    // Dominio del Modulo Evento
    private final IEventoNotificacionService eventoService;

    private final CurrentUserProvider currentUserProvider;

    private final Logger log = LogManager.getLogger(CreateVentaServiceImpl.class);

    @Override
    @Transactional
    public VentaResponse execute(VentaRequest request) {
        
        this.validarRequest(request);
        log.info("request valido");
        final Long tenantId = currentUserProvider.getUserTenantId();
        final Long userId = currentUserProvider.getUserId();
        final Venta newVenta = VentaMapper.toEntity.apply(request);
        newVenta.setTenantId(tenantId);
        newVenta.setVendedorId(userId);
        log.info("request mapeado a entity");
        
        if(request.getEstado().equals(EnumVenta.Estado.VENTA.name()) ) {
            final var detalleProductoConStock = request.getDetalle().stream().
                    filter(DetalleVentaRequest::getSeControlaStock).toList();

            if(!detalleProductoConStock.isEmpty()) {
                log.info("Es tipo Venta Directa con movimiento");
                final Long movimientoId = registrarMovimientoService.registrar(detalleProductoConStock);
                newVenta.setMovimientoId(movimientoId);
            }
        }
        List<DetalleVenta> detalles = this.procesarDetalleCalculos(newVenta);

        newVenta.setDetalle(detalles);
        BigDecimal totalVenta = detalles.stream()
                .map(DetalleVenta::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        newVenta.setTotal(totalVenta);

        final VentaResponse ventaResponse = VentaMapper.toResponse
                .apply(repository.save(newVenta));
        log.info("venta creada " + ventaResponse);
        
        if(newVenta.getEstado().equals(EnumVenta.Estado.VENTA.name())) {
            this.crearPagos(request.getPagos(), ventaResponse.getId());
        }
        //proceso Asyncrono
        this.eventoService.procesarEventosPendientes();
        return ventaResponse;
    }

    private List<DetalleVenta> procesarDetalleCalculos(Venta venta) {
        return venta.getDetalle().stream()
                .map( (detalle) -> {
                    // el precio ya lo hago en el Frontend
                    BigDecimal precio = detalle.getPrecio();
                    BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(detalle.getCantidad()));
                    detalle.setSubtotal(subtotal);
                    return detalle;
                }).toList();
    }

    // REF: reulizado
    private void validarRequest(VentaRequest request) {
        final Long tenantId =  currentUserProvider.getUserTenantId();
        clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente","id", request.getClienteId()));

        repository.findByCodigoAndTenantId(request.getCodigo(), tenantId)
                .ifPresent(venta -> {
                    throw new DuplicateEntityException("Venta","codigo", request.getCodigo());
                });

        if(!this.esEstadoValido(request.getEstado())) {
            throw new EntityNotFoundException("EstadoVenta","nombre", request.getEstado());
        }

        request.getDetalle().forEach(this::validarDetalleAndGenerarEventoNotificacion);
    }

    // REF: el enun deberia disponer un metodo q valide
    private boolean esEstadoValido(String estado) {
        // return  EnumVenta.Estado.exists(estado);
        return Arrays.stream(EnumVenta.Estado.values())
                .anyMatch(e -> e.name().equals(estado));
    }

    private void validarDetalleAndGenerarEventoNotificacion(DetalleVentaRequest detalle) {

        ProductoPresentacion productoPresentacion = productoRepository.findById(detalle.getPresentacionId())
                .orElseThrow(()-> new EntityNotFoundException("Presentacion","id",detalle.getPresentacionId()));

        if(detalle.getSeControlaStock()) {
            log.info("seControlaStock "+ " para presentacion " + productoPresentacion.getId());
            final Integer cantidadDisponibleEnStock = stockRepository.getCantidadStockDisponibleByProducto(
                    productoPresentacion.getProductoId(), productoPresentacion.getId());

            this.registrarEventoNotificacion(productoPresentacion, cantidadDisponibleEnStock, detalle.getCantidad());

            if (cantidadDisponibleEnStock < detalle.getCantidad()) {
                throw new BusinessRuleException("Presentacion",
                        EnumVenta.Rules.STOCK_INSUFICIENTE.name() ,
                        Map.of("id", productoPresentacion.getId(),"disponible", cantidadDisponibleEnStock, "cantidad", detalle.getCantidad())
                );
            }
        }
    }

    private void registrarEventoNotificacion(ProductoPresentacion presentacion,
                                             Integer cantidadDisponibleEnStock, Integer cantidadAVender) {

        final Integer cantidadDisponibleReal = cantidadDisponibleEnStock - cantidadAVender;

        if (cantidadDisponibleReal <= presentacion.getCantidadMinimoStock()) {
            EventoNotificacion event = eventoService.registrarEvento(EnumEvento.Type.STOCK_BAJO.name(), presentacion.getId());
            log.info("Se creado evento quiebre type STOCK_BAJO: " + event);
        }

        if (cantidadDisponibleEnStock <= 0 ) {
            EventoNotificacion event = eventoService.registrarEvento(EnumEvento.Type.SIN_STOCK.name(), presentacion.getId());
            log.info("Se creado evento type SIN_STOCK: " + event);
        }
    }

    // REF: y reutilizar
    private void crearPagos(PagoRequest pagoRequest, Long ventaId) {
        pagoRequest.setVentaId(ventaId);
        log.info("Creamos Pagos " + pagoRequest);
        final PagoResponse savePagos = pagoService.save(pagoRequest);
        log.info("creados Pagos " + savePagos);
    }

    public CreateVentaServiceImpl(
            IVentaRepository repository,
            IClienteRepository clienteRepository,
            IPagoService pagoService,
            IProductoPresentacionRepository productoRepository,
            IRegistrarMovimientoService registrarMovimientoService,
            IStockRepository stockRepository,
            IEventoNotificacionService eventoService,
            CurrentUserProvider currentUserProvider
    ) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
        this.pagoService = pagoService;
        this.productoRepository = productoRepository;
        this.registrarMovimientoService = registrarMovimientoService;
        this.stockRepository = stockRepository;
        this.eventoService = eventoService;
        this.currentUserProvider = currentUserProvider;
    }
}
