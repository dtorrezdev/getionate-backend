package com.dtorrez.main.modulos.producto.services.presentacion.create;

import com.dtorrez.main.common.enums.EnumEvento;
import com.dtorrez.main.common.exceptions.DuplicateEntityException;
import com.dtorrez.main.common.exceptions.EntityNotFoundException;
import com.dtorrez.main.common.providers.CurrentUserProvider;
import com.dtorrez.main.modulos.evento.services.EventoNotificaconServiceImpl;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.*;
import com.dtorrez.main.modulos.producto.mappers.ProductoPresentacionMapper;
import com.micrium.bd.access.jpa.modulo.productos.models.ProductoPresentacion;
import com.micrium.bd.access.jpa.modulo.productos.repository.IMarcaRepository;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoRepository;
import com.micrium.bd.access.jpa.modulo.productos.repository.IUnidadMedidaRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateProductoPresentacionServiceImpl implements ICreateProductoPresentacionService {

    @Autowired
    private IProductoPresentacionRepository repository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

    private final Logger log = LogManager.getLogger(CreateProductoPresentacionServiceImpl.class);

    @Override
    public ProductoPresentacionResponse execute(ProductoPresentacionRequest request) {
        log.info("execute create service PP");
        this.validateRequest(request);
        final Long tenantId = currentUserProvider.getUserTenantId();
        final Long userId = currentUserProvider.getUserId();
        log.info("execute create service PP userId: " + userId);
        final ProductoPresentacion prodPresentacion = ProductoPresentacionMapper
                .fromRequestToEntity
                .apply(request);
        prodPresentacion.setTenantId(tenantId);
        prodPresentacion.setUsuarioId(userId);
        log.info("print: " + prodPresentacion);

        final ProductoPresentacionResponse createdProducto = ProductoPresentacionMapper.toResponse
                .apply(repository.save(prodPresentacion));

        if(request.getSeControlaStock()) {
            this.registrarEventoProducto(createdProducto);
        }
        return createdProducto;
    }

    @Autowired
    private EventoNotificaconServiceImpl eventoNotificacionService;
    // Refactorizar
    private void registrarEventoProducto(ProductoPresentacionResponse producto) {

        if(producto.getDiasAntesExpiracion() == null || producto.getDiasAntesExpiracion() <= 1) {
            eventoNotificacionService.registrarEventoYNotificacionProducto(
                    EnumEvento.Type.PROD_SIN_DIAS_ANTES_EXPIRACION.name(),
                    producto.getId());
        }

        if(producto.getCantidadMinimoStock() == null || producto.getCantidadMinimoStock() <= 1) {
            eventoNotificacionService.registrarEventoYNotificacionProducto(
                    EnumEvento.Type.PROD_SIN_MIN_STOCK_DISPONIBLE.name(),
                    producto.getId());
        }
    }

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private IUnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    private IMarcaRepository marcaRepository;

    private void validateRequest(ProductoPresentacionRequest request) {

        productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new EntityNotFoundException("Producto", "id", request.getProductoId()));

        unidadMedidaRepository.findById(request.getUnidadMedidaId())
                .orElseThrow(()-> new EntityNotFoundException("Unidad Medida", "id", request.getUnidadMedidaId()));

        marcaRepository.findById(request.getMarcaId())
                .orElseThrow(()-> new EntityNotFoundException("Marca", "id", request.getMarcaId()));

        repository.findByNombreAndProductoIdAndMarcaId(
                request.getNombre().toUpperCase(),
                request.getProductoId(),
                request.getMarcaId())
                .ifPresent((ele)-> {
                    throw new DuplicateEntityException("Presentacion",
                            "nombre,productoId,marcaId,", String.format("%s,%s,%s", request.getNombre(), request.getProductoId(), request.getMarcaId()));
                });
    }

}
