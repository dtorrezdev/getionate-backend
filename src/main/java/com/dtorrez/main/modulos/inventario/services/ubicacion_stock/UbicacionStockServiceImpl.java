package com.dtorrez.main.modulos.inventario.services.ubicacion_stock;

import com.dtorrez.main.common.exceptions.EntityNotFoundException;
import com.dtorrez.main.common.providers.CurrentUserProvider;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.ubicacion_stock.*;
import com.dtorrez.main.modulos.inventario.mapper.UbicacionStockMapper;
import com.micrium.bd.access.jpa.modulo.inventario.models.UbicacionStock;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IUbicacionStockRepository;
import com.micrium.bd.access.jpa.modulo.productos.models.ProductoPresentacion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UbicacionStockServiceImpl implements IUbicacionStockService {

    @Autowired
    private IUbicacionStockRepository repository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

    private final Logger log = LogManager.getLogger(UbicacionStockServiceImpl.class);

    @Override
    public Page<UbicacionStockResponse> list(UbicacionStockRequest request, Pageable page) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        log.info("params: " + request);
        log.info("page: " + page);

        return repository.filter(
                queryfilterTexto(request.getSeccion()),
                filterTextoQueryUpperLike(request.getSeccion()),
                queryfilterTexto(request.getEstante()),
                filterTextoQueryUpperLike(request.getEstante()),
                queryfilterTexto(request.getNivel()),
                filterTextoQueryUpperLike(request.getNivel()),
                page, tenantId)
                .map(UbicacionStockMapper.fromEntityToResponse);
    }

    @Override
    public UbicacionStockResponse create(UbicacionStockRequest request) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        final UbicacionStock newUbicacion = UbicacionStockMapper.toEntity.apply(request);
        newUbicacion.setTenantId(tenantId);

        return UbicacionStockMapper.fromEntityToResponse
                .apply(repository.save(newUbicacion));
    }

    @Override
    public UbicacionStockResponse update(UbicacionStockRequest request, Long id) {
        return repository.findById(id)
                .map(ubicacionStock -> {
                    ubicacionStock.setSeccion(request.getSeccion());
                    ubicacionStock.setEstante(request.getEstante());
                    ubicacionStock.setNivel(request.getNivel());
                    return ubicacionStock;
                })
                .map(repository::save)
                .map(UbicacionStockMapper.fromEntityToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Ubicacion Stock", "id", id));
    }

    @Override
    public void delete(Long id) {
        final UbicacionStock ubicacionStock = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Ubicacion Stock", "id", id));
        ubicacionStock.setEsActivo(false);
        repository.save(ubicacionStock);

    }

    private boolean isBlanck(String dato) {
        return dato == null || dato.trim().isEmpty();
    }

    private int queryfilterTexto(String texto) {
        return this.isBlanck(texto) ? -1 : 0;
    }

    private String filterTextoQueryUpperLike(String texto) {
        return this.isBlanck(texto) ? "" : "%" + texto.trim().toUpperCase() + "%";
    }
}
