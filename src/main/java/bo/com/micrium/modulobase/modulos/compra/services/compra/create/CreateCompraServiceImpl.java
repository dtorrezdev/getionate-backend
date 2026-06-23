package bo.com.micrium.modulobase.modulos.compra.services.compra.create;

import bo.com.micrium.modulobase.common.enums.EnumCompra;
import bo.com.micrium.modulobase.common.exceptions.DuplicateEntityException;
import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.common.providers.CurrentUserProvider;
import bo.com.micrium.modulobase.modulos.compra.Mappers.CompraMapper;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.crear.DetalleCompraRequest;
import bo.com.micrium.modulobase.modulos.ventas.mapper.VentaMapper;
import bo.com.micrium.modulobase.modulos.ventas.services.venta.create.CreateVentaServiceImpl;
import com.micrium.bd.access.jpa.modulo.compra.models.Compra;
import com.micrium.bd.access.jpa.modulo.compra.models.DetalleCompra;
import com.micrium.bd.access.jpa.modulo.compra.repositories.ICompraRepository;
import com.micrium.bd.access.jpa.modulo.compra.repositories.IProveedorRepository;
import com.micrium.bd.access.jpa.modulo.productos.models.ProductoPresentacion;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.crear.CompraRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.crear.CompraResponse;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CreateCompraServiceImpl implements ICreateCompraService {

    @Autowired
    private ICompraRepository repository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

    private final Logger log = LogManager.getLogger(CreateCompraServiceImpl.class);

    @Override
    public CompraResponse execute(CompraRequest request) {

        // 1. Validar request
        this.validarRequest(request);
        log.info("request valido");
        final Long tenantId = currentUserProvider.getUserTenantId();
        final Compra newCompra = CompraMapper.toEntity.apply(request);
        newCompra.setTenantId(tenantId);
        List<DetalleCompra> detalle = this.procesarDetalleCalculos(newCompra);
        newCompra.setDetalle(detalle);
        BigDecimal totalCompra = detalle.stream()
                .map(DetalleCompra::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        newCompra.setTotal(totalCompra);
        log.info("request mapeado a entity");
        // 2. Mappear
        final CompraResponse response = CompraMapper.toResponse
                .apply(repository.save(newCompra));
        // 3. Registrar
        log.info("create entity");
        if(request.getEstado().equals(EnumCompra.TIPO.SOLICITUD.name())) {
            final String mensaje = response.getMensaje();
            response.setMensaje("Solicitud " + mensaje);
        }
        return response;
    }

    private List<DetalleCompra> procesarDetalleCalculos(Compra compra) {
        return compra.getDetalle().stream()
                .map( (detalle) -> {
                    BigDecimal precio = detalle.getPrecio();
                    BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(detalle.getCantidad()));
                    detalle.setSubtotal(subtotal);
                    return detalle;
                }).toList();
    }

    @Autowired
    private IProveedorRepository proveedorRepository;

    private void validarRequest(CompraRequest request) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        final var proveedorId = request.getProveedorId();

        proveedorRepository.findById(proveedorId)
                .orElseThrow(() -> new EntityNotFoundException("Proveedor","id", proveedorId));

        repository.findByCodigoAndTenantId(request.getCodigo(), tenantId)
                .ifPresent(compra -> {
                    throw new DuplicateEntityException("Compra","codigo", request.getCodigo());
                });
        if(!EnumCompra.Estado.exists(request.getEstado())) {
            throw new EntityNotFoundException("EstadoCompra", "nombre", request.getEstado());
        }
        request.getDetalle().forEach(this::validarDetalle);
    }

    // Dominio del Modulo Producto
    @Autowired
    private IProductoPresentacionRepository productoRepository; // eliminar dependencia

    private void validarDetalle(DetalleCompraRequest detalle) {
        productoRepository.findById(detalle.getPresentacionId())
                .orElseThrow(()-> new EntityNotFoundException("Presentacion","id",detalle.getPresentacionId()));
    }
}
