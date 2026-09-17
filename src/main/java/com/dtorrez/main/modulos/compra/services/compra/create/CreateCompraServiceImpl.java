package com.dtorrez.main.modulos.compra.services.compra.create;

import com.dtorrez.main.common.enums.EnumCompra;
import com.dtorrez.main.common.exceptions.DuplicateEntityException;
import com.dtorrez.main.common.exceptions.EntityNotFoundException;
import com.dtorrez.main.common.providers.CurrentUserProvider;
import com.dtorrez.main.modulos.compra.Mappers.OrdenCompraMapper;
import com.dtorrez.main.modulos.compra.Mappers.SolicitudCompraMapper;
import com.dtorrez.main.modulos.compra.controllers.dtos.orden_compra.crear.DetalleCompraRequest;
import com.micrium.bd.access.jpa.modulo.compra.models.Compra;
import com.micrium.bd.access.jpa.modulo.compra.models.DetalleCompra;
import com.micrium.bd.access.jpa.modulo.compra.repositories.ICompraRepository;
import com.micrium.bd.access.jpa.modulo.compra.repositories.IProveedorRepository;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dtorrez.main.modulos.compra.controllers.dtos.orden_compra.crear.CompraRequest;
import com.dtorrez.main.modulos.compra.controllers.dtos.orden_compra.crear.CompraResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class CreateCompraServiceImpl implements ICreateCompraService {

    @Autowired
    private ICompraRepository repository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

    private final Logger log = LogManager.getLogger(CreateCompraServiceImpl.class);

    @Override
    public CompraResponse createOrden(CompraRequest request) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        final Long usuarioId = currentUserProvider.getUserId();

        // 1. Validar request
        this.validarRequest(request, Boolean.FALSE);
        log.info("request valido");

        final Compra newCompra = OrdenCompraMapper.toEntity.apply(request);
        newCompra.setTenantId(tenantId);
        List<DetalleCompra> detalle = this.procesarDetalleCalculos(newCompra, Boolean.FALSE);
        newCompra.setDetalle(detalle);
        BigDecimal totalCompra = detalle.stream()
                .map(DetalleCompra::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        newCompra.setTotal(totalCompra);
        newCompra.setEsSolicitud(Boolean.FALSE);
        newCompra.setSolicitanteId(usuarioId);
        log.info("request mapeado a entity");
        // 2. Mappear & Registrar
        return OrdenCompraMapper.toResponse
                .apply(repository.save(newCompra));
    }

    @Override
    public CompraResponse createSolicitud(CompraRequest request) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        final Long usuarioId = currentUserProvider.getUserId();

        this.validarRequest(request, Boolean.TRUE);
        log.info("request valido");

        final Compra newCompra = SolicitudCompraMapper.toEntity.apply(request);
        newCompra.setTenantId(tenantId);
        List<DetalleCompra> detalle = this.procesarDetalleCalculos(newCompra, Boolean.TRUE);
        newCompra.setDetalle(detalle);
        BigDecimal totalCompra = detalle.stream()
                .map(DetalleCompra::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        newCompra.setTotal(totalCompra);
        newCompra.setEsSolicitud(Boolean.TRUE);
        newCompra.setSolicitanteId(usuarioId);
        log.info("request mapeado a entity");

        // 2. Mappear & Registrar
        return SolicitudCompraMapper.toResponse
                .apply(repository.save(newCompra));
    }

    private List<DetalleCompra> procesarDetalleCalculos(Compra compra, Boolean esSolicitud) {
        return compra.getDetalle().stream()
                .map( (detalle) -> {
                    BigDecimal precio = detalle.getPrecio();
                    BigDecimal subtotal = precio.multiply(BigDecimal.
                            valueOf(detalle.getCantidadSolicitado()));
                    detalle.setSubtotal(subtotal);
                    return detalle;
                }).toList();
    }

    @Autowired
    private IProveedorRepository proveedorRepository;

    private void validarRequest(CompraRequest request, Boolean isSolicitud) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        final var proveedorId = request.getProveedorId();

        if(Objects.nonNull(proveedorId)) {
            proveedorRepository.findById(proveedorId)
                    .orElseThrow(() -> new EntityNotFoundException("Proveedor","id", proveedorId));
        }

        if(isSolicitud) {
            repository.findByCodigoSolicitudAndTenantId(request.getCodigo(), tenantId)
                .ifPresent(compra -> {
                    throw new DuplicateEntityException("SolicitudCompra","codigo", request.getCodigo());
                });
        } else {
            repository.findByCodigoCompraAndTenantId(request.getCodigo(), tenantId)
                .ifPresent(compra -> {
                    throw new DuplicateEntityException("OrdenCompra","codigo", request.getCodigo());
                });
        }

        if(Objects.nonNull(request.getEstado())) {
            if (!EnumCompra.EstadoSolicitud.exists(request.getEstado())) {
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

    // Dominio del Modulo Producto
    @Autowired
    private IProductoPresentacionRepository productoRepository; // eliminar dependencia

    private void validarDetalle(DetalleCompraRequest detalle) {
        productoRepository.findById(detalle.getPresentacionId())
                .orElseThrow(()-> new EntityNotFoundException("Presentacion","id",detalle.getPresentacionId()));
    }
}
