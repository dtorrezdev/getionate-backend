package bo.com.micrium.modulobase.modulos.ventas.services.venta.update;

import bo.com.micrium.modulobase.common.enums.EnumEvento;
import bo.com.micrium.modulobase.common.enums.EnumVenta;
import bo.com.micrium.modulobase.common.exceptions.BusinessRuleException;
import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.common.providers.CurrentUserProvider;
import bo.com.micrium.modulobase.modulos.evento.services.IEventoNotificacionService;
import bo.com.micrium.modulobase.modulos.inventario.services.movimiento.registrar.IRegistrarMovimientoService;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.pago.PagoRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.pago.PagoResponse;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.DetalleVentaRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.VentaResponse;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.update.VentaUpdateRequest;
import bo.com.micrium.modulobase.modulos.ventas.mapper.VentaMapper;
import bo.com.micrium.modulobase.modulos.ventas.services.pago.IPagoService;
import com.micrium.bd.access.jpa.modulo.eventos.models.EventoNotificacion;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IStockRepository;
import com.micrium.bd.access.jpa.modulo.productos.models.ProductoPresentacion;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import com.micrium.bd.access.jpa.modulo.venta.models.DetalleVenta;
import com.micrium.bd.access.jpa.modulo.venta.models.Venta;
import com.micrium.bd.access.jpa.modulo.venta.repository.IClienteRepository;
import com.micrium.bd.access.jpa.modulo.venta.repository.IDetalleVenta;
import com.micrium.bd.access.jpa.modulo.venta.repository.IVentaRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UpdateVentaServiceImp implements IUpdateVentaService {
    // Dominio del Modulo Venta
    private final IVentaRepository repository;
    private final IDetalleVenta detalleRepository;
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

    private final Logger log = LogManager.getLogger(UpdateVentaServiceImp.class);

    @Override
    @Transactional
    public VentaResponse execute(VentaUpdateRequest request, Long id) {
        /*1. validar el request*/
        this.validarRequest(request, id);
        log.info("request valido");

        /*2. transformar la data (Mapping)*/
        final Venta updateVenta = VentaMapper.fromUpdatetoEntity.apply(request);
        final Long tenantId = currentUserProvider.getUserTenantId();
        final Long usuarioId = currentUserProvider.getUserId();
        updateVenta.setTenantId(tenantId);
        updateVenta.setVendedorId(usuarioId);
        log.info("request mapeado a entity");


        /*3. Estoy seguro q va ser venta (no esta demas validar) para movimiento*/
        if(request.getEstado().equals(EnumVenta.Estado.VENTA.name()) ) {
            final var detalleProductoConStock = request.getDetalle().stream().
                    filter(DetalleVentaRequest::getSeControlaStock).toList();

            if(!detalleProductoConStock.isEmpty()) {
                log.info("Es tipo Venta Directa con movimiento");
                final Long movimientoId = registrarMovimientoService.registrar(detalleProductoConStock);
                updateVenta.setMovimientoId(movimientoId);
            }
        }

        /*4. procesar Detalle */
        this.eliminarDetalleVentaExistente(updateVenta.getId());
        List<DetalleVenta> detalles = this.procesarDetalleCalculos(updateVenta.getDetalle());

        /* 5. Guardar Venta con datos actuales */
        updateVenta.setDetalle(detalles);
        BigDecimal totalVenta = detalles.stream()
                .map(DetalleVenta::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        updateVenta.setTotal(totalVenta);

        final VentaResponse ventaResponse = VentaMapper.toResponse
                .apply(repository.save(updateVenta));
        log.info("venta update " + ventaResponse);
        /*6. Crear pago (validar si es venta)*/
        if(updateVenta.getEstado().equals(EnumVenta.Estado.VENTA.name())) {
            this.crearPagos(request.getPagos(), ventaResponse.getId());
        }
        /*7. registar eventos Existente*/
        this.eventoService.procesarEventosPendientes();
        // 8. Dar respuesta
        return ventaResponse;
    }

    private void eliminarDetalleVentaExistente(Long ventaId) {
        detalleRepository.deleteByVentaId(ventaId);
    }

    private List<DetalleVenta> procesarDetalleCalculos(List<DetalleVenta> detalles) {
        return detalles.stream()
                .map( (detalle) -> {
                    // el precio ya lo hago en el Frontend
                    BigDecimal precio = detalle.getPrecio();
                    BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(detalle.getCantidad()));
                    detalle.setSubtotal(subtotal);
                    return detalle;
                }).toList();
    }

    // REF: reulizado
    private void validarRequest(VentaUpdateRequest request, Long ventaId) {

        repository.findById(ventaId)
                .orElseThrow(() -> new EntityNotFoundException("Venta","id", ventaId));

        clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente","id", request.getClienteId()));

        if(!this.esEstadoValido(request.getEstado())) {
            throw new EntityNotFoundException("Estado Venta", "nombre" ,request.getEstado());
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
                .orElseThrow(()-> new EntityNotFoundException("Presentacion","id", detalle.getPresentacionId()));

        if(detalle.getSeControlaStock()) {
            log.info(" secontrala Stock");
            final Integer cantidadDisponibleEnStock = stockRepository.getCantidadStockDisponibleByProducto(
                    productoPresentacion.getProductoId(), productoPresentacion.getId());

            this.registrarEventoNotificacion(productoPresentacion, cantidadDisponibleEnStock, detalle.getCantidad());

            if (cantidadDisponibleEnStock < detalle.getCantidad()) {
                throw new BusinessRuleException("Presentacion",
                        EnumVenta.Rules.STOCK_INSUFICIENTE.name(),
                        Map.of("id", productoPresentacion.getId(), "disponible", cantidadDisponibleEnStock, "cantidad", detalle.getCantidad())
                );
            }
        }
    }

    private void registrarEventoNotificacion(ProductoPresentacion presentacion,
                                             Integer cantidadDisponibleEnStock, Integer cantidadAVender) {

        final Integer cantidadDisponibleReal = cantidadDisponibleEnStock - cantidadAVender;
        log.info("cantidadDisponibleEnStock: " + cantidadDisponibleEnStock + " cantidadAVender: "+ presentacion.getId());
        log.info("cantidadDisponibleReal: "+cantidadDisponibleReal + " prodId: "+ presentacion.getId());
        if (cantidadDisponibleReal <= presentacion.getCantidadMinimoStock()) {
            EventoNotificacion event = eventoService.registrarEvento(EnumEvento.Type.STOCK_BAJO.name(), presentacion.getId());
            log.info("Se creado evento quiebre type STOCK_BAJO: " + event);
        }

        if (cantidadDisponibleReal <= 0 ) {
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

    public UpdateVentaServiceImp(
            IVentaRepository repository,
            IDetalleVenta detalleRepository,
            IClienteRepository clienteRepository,
            IPagoService pagoService,
            IProductoPresentacionRepository productoRepository,
            IRegistrarMovimientoService registrarMovimientoService,
            IStockRepository stockRepository,
            IEventoNotificacionService eventoService,
            CurrentUserProvider currentUserProvider
    ) {
        this.repository = repository;
        this.detalleRepository = detalleRepository;
        this.clienteRepository = clienteRepository;
        this.pagoService = pagoService;
        this.productoRepository = productoRepository;
        this.registrarMovimientoService = registrarMovimientoService;
        this.stockRepository = stockRepository;
        this.eventoService = eventoService;
        this.currentUserProvider = currentUserProvider;
    }
}
