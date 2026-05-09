package bo.com.micrium.modulobase.modulos.compra.services.recepcion.create;

import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.modulos.compra.Mappers.RecepcionProductoMapper;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.crear.DetalleRecepcionRequest;
import bo.com.micrium.modulobase.modulos.inventario.services.movimiento.registrar.IRegistrarMovimientoService;
import com.micrium.bd.access.jpa.modulo.compra.models.Compra;
import com.micrium.bd.access.jpa.modulo.compra.models.DetalleCompra;
import com.micrium.bd.access.jpa.modulo.compra.models.DetalleRecepcion;
import com.micrium.bd.access.jpa.modulo.compra.models.RecepcionProducto;
import com.micrium.bd.access.jpa.modulo.compra.repositories.ICompraRepository;
import com.micrium.bd.access.jpa.modulo.compra.repositories.IRecepcionProductoRepository;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.crear.RecepcionProductoRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.crear.RecepcionProductoResponse;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CreateRecepcionProductoServiceImpl implements ICreateRecepcionProductoService {

    @Autowired
    private IRecepcionProductoRepository repository;

    @Autowired
    private ICompraRepository compraRepository;

    @Autowired
    private IProductoPresentacionRepository productoRepository;

    @Autowired
    private IRegistrarMovimientoService registrarMovimientoService;

    private final Logger log = LogManager.getLogger(CreateRecepcionProductoServiceImpl.class);

    @Override
    @Transactional
    public RecepcionProductoResponse execute(RecepcionProductoRequest request) {

        // 1. Validar request
        this.validarRequest(request);
        log.info("request valido");

        // 2. Mapear request a entity
        final RecepcionProducto newRecepcion = RecepcionProductoMapper.toEntity.apply(request);
        List<DetalleRecepcion> detalle = this.procesarDetalleCalculos(newRecepcion);
        newRecepcion.setDetalle(detalle);
        BigDecimal totalRecepcion = detalle.stream()
                .map(DetalleRecepcion::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        newRecepcion.setTotal(totalRecepcion);
        log.info("request mapeado a entity");

        // crear movimientos
        final Long movimientoId = registrarMovimientoService.registrarRecepcion(request.getDetalle());

        newRecepcion.setMovimientoId(movimientoId);

        // 3. Guardar en repositorio
        return RecepcionProductoMapper.toResponse
                .apply(repository.save(newRecepcion));
    }

    private List<DetalleRecepcion> procesarDetalleCalculos(RecepcionProducto recepcion) {
        return recepcion.getDetalle().stream()
                .map( (detalle) -> {
                    BigDecimal precio = detalle.getPrecio();
                    BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(detalle.getCantidad()));
                    detalle.setSubtotal(subtotal);
                    return detalle;
                }).toList();
    }

    private void validarRequest(RecepcionProductoRequest request) {
        // Validar que el compra existe
        final var compraId = request.getCompraId();
        compraRepository.findById(compraId)
                .orElseThrow(() -> new EntityNotFoundException("Compra", "id", compraId));
        // Validar todos los detalles
        request.getDetalle().forEach(this::validarDetalle);
    }

    private void validarDetalle(DetalleRecepcionRequest detalle) {
        // Validar que la presentación existe
        productoRepository.findById(detalle.getPresentacionId())
                .orElseThrow(() -> new EntityNotFoundException("Presentacion", "id", detalle.getPresentacionId()));
    }
}

