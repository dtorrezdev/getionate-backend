package com.dtorrez.main.modulos.producto.services.unidad_medida;

import com.dtorrez.main.common.exceptions.EntityNotFoundException;
import com.dtorrez.main.common.providers.CurrentUserProvider;
import com.dtorrez.main.modulos.producto.controllers.dtos.unidad_medida.UnidadMedidaRequest;
import com.dtorrez.main.modulos.producto.controllers.dtos.unidad_medida.UnidadMedidaResponse;
import com.dtorrez.main.modulos.producto.mappers.UnidadMedidaMapper;
import com.micrium.bd.access.jpa.modulo.productos.models.ProductoPresentacion;
import com.micrium.bd.access.jpa.modulo.productos.models.UnidadMedida;
import com.micrium.bd.access.jpa.modulo.productos.repository.IUnidadMedidaRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UnidadMedidaServiceImpl implements  IUnidadMedidaService {

    @Autowired
    private IUnidadMedidaRepository repository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

    private final Logger log = LogManager.getLogger(UnidadMedidaServiceImpl.class);

    @Override
    public Page<UnidadMedidaResponse> list(UnidadMedidaRequest request, Pageable pageRequest) {
        final Long tenantId = currentUserProvider.getUserTenantId();

        log.info(" list(): request " + request);

        return repository.filter(
                queryfilterTexto(request.getAbreviatura()),
                filterTextoQueryUpperLike(request.getAbreviatura()),
                queryfilterTexto(request.getNombre()),
                filterTextoQueryUpperLike(request.getNombre()),
                pageRequest, tenantId
        ).map(UnidadMedidaMapper.fromEntityToResponse);
    }

    @Override
    public UnidadMedidaResponse create(UnidadMedidaRequest request) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        final UnidadMedida newUnidadMedida = UnidadMedidaMapper
                .fromRequestToEntity.apply(request);
        newUnidadMedida.setTenantId(tenantId);

        return UnidadMedidaMapper.fromEntityToResponse
                .apply(repository.save(newUnidadMedida));
    }

    @Override
    public UnidadMedidaResponse update(UnidadMedidaRequest request, Long id) {
        return repository.findById(id)
                .map(unidadMedida -> {
                    unidadMedida.setAbreviatura(request.getAbreviatura());
                    unidadMedida.setNombre(request.getNombre());
                    unidadMedida.setEsUnidadMinima(Boolean.valueOf(request.getEsUnidadMinima()));
                    return unidadMedida;
                })
                .map(repository::save)
                .map(UnidadMedidaMapper.fromEntityToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Unidad Medida", "id", id));
    }

    @Override
    public void delete(Long id) {
        final UnidadMedida unidadMedida = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Unidad Medida", "id", id));
        unidadMedida.setEsActivo(false);
        repository.save(unidadMedida);
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
