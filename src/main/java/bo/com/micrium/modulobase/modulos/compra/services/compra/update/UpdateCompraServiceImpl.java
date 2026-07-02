package bo.com.micrium.modulobase.modulos.compra.services.compra.update;

import bo.com.micrium.modulobase.common.enums.EnumCompra;
import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.common.providers.CurrentUserProvider;
import bo.com.micrium.modulobase.modulos.compra.Mappers.OrdenCompraMapper;
import bo.com.micrium.modulobase.modulos.compra.Mappers.SolicitudCompraMapper;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.crear.DetalleCompraRequest;
import com.micrium.bd.access.jpa.modulo.compra.models.Compra;
import com.micrium.bd.access.jpa.modulo.compra.models.DetalleCompra;
import com.micrium.bd.access.jpa.modulo.compra.repositories.ICompraRepository;
import com.micrium.bd.access.jpa.modulo.compra.repositories.IDetalleCompraRepository;
import com.micrium.bd.access.jpa.modulo.compra.repositories.IProveedorRepository;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.crear.CompraResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.update.CompraUpdateRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class UpdateCompraServiceImpl implements IUpdateCompraService {

    @Autowired
    private ICompraRepository repository;

    @Autowired
    private IDetalleCompraRepository detalleRepository;

    @Autowired
    private IProveedorRepository proveedorRepository;

    // Dominio del Modulo Producto
    @Autowired
    private IProductoPresentacionRepository productoRepository; // eliminar dependencia

    @Autowired
    private CurrentUserProvider currentUserProvider;

    private final Logger log = LogManager.getLogger(UpdateCompraServiceImpl.class);

    @Override
    @Transactional
    public CompraResponse updateOrder(CompraUpdateRequest request, Long id) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        final Long usuarioId = currentUserProvider.getUserId();

        // validar Request
        this.validarRequest(request, id, Boolean.FALSE);
        log.info("request valido");

        // transformar la data (Mapping)
        final Compra updateCompra = OrdenCompraMapper.fromUpdatetoEntity.apply(request);
        updateCompra.setTenantId(tenantId);
        updateCompra.setId(id);
        log.info("request mapeado a entity " + updateCompra);

        // procesar Detalle
        this.eliminarDetalleCompraExistente(id);
        List<DetalleCompra> detalle = this.procesarDetalleCalculos(updateCompra);
        updateCompra.setDetalle(detalle);
        BigDecimal totalCompra = detalle.stream()
                .map(DetalleCompra::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        updateCompra.setTotal(totalCompra);
        updateCompra.setEsSolicitud(Boolean.FALSE);
        updateCompra.setSolicitanteId(usuarioId);
        log.info("request processed " + updateCompra);

        // Guardar compra & Registrar
        return OrdenCompraMapper.toResponse
                .apply(repository.save(updateCompra));
    }

    @Override
    public CompraResponse updateSolicitud(CompraUpdateRequest request, Long id) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        final Long usuarioId = currentUserProvider.getUserId();

        // validar Request
        this.validarRequest(request, id, Boolean.TRUE);
        log.info("request valido");

        // transformar la data (Mapping)
        final Compra updateCompra = SolicitudCompraMapper.fromUpdatetoEntity.apply(request);
        updateCompra.setTenantId(tenantId);
        updateCompra.setId(id);
        log.info("request mapeado a entity " + updateCompra);

        // procesar Detalle
        this.eliminarDetalleCompraExistente(id);
        List<DetalleCompra> detalle = this.procesarDetalleCalculos(updateCompra);
        updateCompra.setDetalle(detalle);
        BigDecimal totalCompra = detalle.stream()
                .map(DetalleCompra::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        updateCompra.setTotal(totalCompra);
        updateCompra.setEsSolicitud(Boolean.TRUE);
        updateCompra.setSolicitanteId(usuarioId);
        log.info("request processed " + updateCompra);

        // Guardar compra & Registrar
        return OrdenCompraMapper.toResponse
                .apply(repository.save(updateCompra));
    }

    @Override
    @Transactional
    public void aprobar(Long id) {

        final var usuarioId = currentUserProvider.getUserId();
        var compra = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Compra","id", id));

        compra.setEstado(EnumCompra.EstadoSolicitud.APROBADO.name());
        compra.setAprobadorId(usuarioId);
        repository.save(compra);
    }

    private List<DetalleCompra> procesarDetalleCalculos(Compra compra) {
        return compra.getDetalle().stream()
                .map( (detalle) -> {
                    BigDecimal precio = detalle.getPrecio();
                    BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(detalle.getCantidadSolicitado()));
                    detalle.setSubtotal(subtotal);
                    return detalle;
                }).toList();
    }

    private void validarRequest(CompraUpdateRequest request, Long compraId, Boolean isSolicitud) {
        final var proveedorId = request.getProveedorId();

        repository.findById(compraId)
                .orElseThrow(() -> new EntityNotFoundException("Compra","id", compraId));

        if(Objects.nonNull(proveedorId)) {
            proveedorRepository.findById(proveedorId)
                    .orElseThrow(() -> new EntityNotFoundException("Proveedor", "id", proveedorId));
        }
        if(Objects.nonNull(request.getEstado())) {
            if(!EnumCompra.EstadoSolicitud.exists(request.getEstado())) {
                throw new EntityNotFoundException("Estado", "nombre", request.getEstado());
            }
        }
        if(Objects.nonNull(request.getTipo())) {
            if (!EnumCompra.TIPO.exists(request.getTipo())) {
                throw new EntityNotFoundException("TipoCompra", "nombre", request.getTipo());
            }
        }
        request.getDetalle().forEach(this::validarDetalle);
    }

    private void validarDetalle(DetalleCompraRequest detalle) {
        productoRepository.findById(detalle.getPresentacionId())
                .orElseThrow(()-> new EntityNotFoundException("Presentacion","id",detalle.getPresentacionId()));
    }

    private void eliminarDetalleCompraExistente(Long compraId) {
        detalleRepository.deleteByCompraId(compraId);
    }
}

