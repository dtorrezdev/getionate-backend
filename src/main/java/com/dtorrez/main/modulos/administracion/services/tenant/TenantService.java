package com.dtorrez.main.modulos.administracion.services.tenant;

import com.dtorrez.main.common.exceptions.EntityNotFoundException;
import com.dtorrez.main.modulos.administracion.controllers.dtos.tenant.TenantRequest;
import com.dtorrez.main.modulos.administracion.controllers.dtos.tenant.TenantResponse;
import com.dtorrez.main.modulos.administracion.mappers.TenantMapper;
import com.micrium.bd.access.jpa.modulo.administracion.models.Tenant;
import com.micrium.bd.access.jpa.modulo.administracion.repositories.ITenantRepository;
import com.micrium.bd.access.jpa.modulo.administracion.repositories.IThemeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TenantService implements ITenantService {

    private final ITenantRepository repository;
    private final IThemeRepository themeRespository;

    @Override
    public Page<TenantResponse> list(TenantRequest request, Pageable pageable) {
        return repository.findByEsActivo(Boolean.TRUE, pageable)
                .map(TenantMapper.toResponse);
    }

    @Override
    public TenantResponse get(Long id) {
        var tenant = repository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Tenant","id", id)
        );
        return TenantMapper.toResponse.apply(tenant);
    }


    @Override
    public TenantResponse create(TenantRequest request) {
        final Tenant newTenant = TenantMapper
                .toEntity.apply(request);

        return TenantMapper.toResponse
                .apply(repository.save(newTenant));
    }

    @Override
    public TenantResponse update(TenantRequest request, Long id) {
        return repository.findById(id)
                .map(tenant -> {
                    tenant.setNombre(request.getNombre());
                    tenant.setLogoUrl(request.getLogoUrl());
                    tenant.setDireccion(request.getDireccion());
                    tenant.setCelular(request.getCelular());
                    tenant.setCiudad(request.getCiudad());
                    return tenant;
                })
                .map(repository::save)
                .map(TenantMapper.toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Tenant", "id", id));
    }

    @Override
    public void delete(String id) {
        final Tenant tenant = repository.findById(Long.valueOf(id))
                .orElseThrow(() ->
                        new EntityNotFoundException("Tenant", "id", id));
        tenant.setEsActivo(Boolean.FALSE);
        repository.save(tenant);
    }

    public TenantService(ITenantRepository repository, IThemeRepository themeRespository) {
        this.repository = repository;
        this.themeRespository = themeRespository;
    }
}
