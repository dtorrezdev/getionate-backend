package bo.com.micrium.modulobase.modulos.ventas.services.venta.create;

import bo.com.micrium.modulobase.common.enums.EnumVenta;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.venta.MovimientoVentaResponse;
import bo.com.micrium.modulobase.modulos.inventario.services.movimiento.ICreateMovimientoVentaService;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.pago.PagoRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.pago.PagoResponse;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.*;
import bo.com.micrium.modulobase.modulos.ventas.mapper.VentaMapper;
import bo.com.micrium.modulobase.modulos.ventas.services.pago.IPagoService;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IStockRepository;
import com.micrium.bd.access.jpa.modulo.productos.models.ProductoPresentacion;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import com.micrium.bd.access.jpa.modulo.venta.repository.*;
import com.micrium.bd.access.jpa.modulo.venta.models.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class CreateVentaServiceImpl implements ICreateVentaService {

    // Dominio del Modulo Venta
    @Autowired
    private IVentaRepository repository;

    @Autowired
    private IDetalleVenta detalleRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    // Dominio del Modulo Producto
    @Autowired
    private IProductoPresentacionRepository productoRepository; // eliminar dependencia

     private final Logger log = LogManager.getLogger(CreateVentaServiceImpl.class);

    // Dominio del Modulo Inventario
    @Autowired
    private ICreateMovimientoVentaService createMovimientoService;

    @Autowired
    private IPagoService pagoService;

    @Override
    @Transactional
    public VentaResponse execute(VentaRequest request) {
        
        this.validarRequest(request);
        log.info("se ha validado");
        final Venta newVenta = VentaMapper.toEntity.apply(request);
        log.info("se ha mappeado ");
        
        if(request.getEstado().equals(EnumVenta.Estado.VENTA.name()) ) {
            final Long movimientoId = this.crearMovimientoAndObtenerId(request.getDetalle());
            newVenta.setMovimientoId(movimientoId);
        }

        List<DetalleVenta> detalles = newVenta.getDetalle().stream()
                .map( (detalle) -> {
                    // el precio ya lo hago en el Frontend
                    BigDecimal precio = detalle.getPrecioUnitario();
                    BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(detalle.getCantidad()));
                    detalle.setSubtotal(subtotal);
                    return detalle;
                }).toList();

        BigDecimal total = detalles.stream()
                .map(DetalleVenta::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        newVenta.setTotal(total);
        log.info("antes de crear newVenta " + newVenta);
        final VentaResponse ventaResponse = VentaMapper.toResponse
                .apply(repository.save(newVenta));
        log.info("despues crear newVenta " + ventaResponse);
        
        if(newVenta.getEstado().equals(EnumVenta.Estado.VENTA.name())) {
            this.crearPagos(request.getPagos(), ventaResponse.getId());
        }
        return ventaResponse;
    }

    private void validarRequest(VentaRequest request) {

        clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no existe"));

        repository.findByCodigo(request.getCodigo())
                .ifPresent(venta -> {
                    throw new RuntimeException("Venta con codigo "+ request.getCodigo() + " ya registrado.");
                });

        if(!this.esEstadoValido(request.getEstado())) {
            throw new RuntimeException("Estado "+request.getEstado()+" no es valido");
        }
        // validar detalle
        request.getDetalle().forEach(this::validarDetalle);
    }

    private boolean esEstadoValido(String estado) {
        return Arrays.stream(EnumVenta.Estado.values())
                .anyMatch(e -> e.name().equals(estado));
    }

    @Autowired
    private IStockRepository stockRepository;

    private void validarDetalle(DetalleVentaRequest detalle) {

        ProductoPresentacion productoPresentacion = productoRepository.findById(detalle.getPresentacionId())
                .orElseThrow(()-> new RuntimeException("Producto no existe"));

        final Integer cantidadStockDisponible = stockRepository.getCantidadStockDisponibleByProducto(
                productoPresentacion.getProductoId(), productoPresentacion.getId());

        if(cantidadStockDisponible < detalle.getCantidad()) {
            throw new RuntimeException("Producto PR-"+ productoPresentacion.getId() +" con stock insuficiente.");
        }
    }

    private Long crearMovimientoAndObtenerId(List<DetalleVentaRequest> detalleVenta) {
        log.info("Es tipo Venta Directa");
        MovimientoVentaResponse movimientoResponse = this.createMovimientoService
                .execute(detalleVenta);

        if(Objects.isNull(movimientoResponse) || movimientoResponse.getId() == null) {
            throw new RuntimeException("Movimiento no se ha creado");
        }
        log.info("movimiento creado -> response: "+ movimientoResponse);
        return movimientoResponse.getId();
    }

    private void crearPagos(PagoRequest pagoRequest, Long ventaId) {
        pagoRequest.setVentaId(ventaId);
        log.info("Creamos Pagos " + pagoRequest);
        final PagoResponse savePagos = pagoService.save(pagoRequest);
        log.info("creados Pagos " + savePagos);
    }
}
