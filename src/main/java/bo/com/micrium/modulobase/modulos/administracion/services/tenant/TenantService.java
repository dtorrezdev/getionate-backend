package bo.com.micrium.modulobase.modulos.administracion.services.tenant;

import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.modulos.administracion.controllers.dtos.tenant.TenantRequest;
import bo.com.micrium.modulobase.modulos.administracion.controllers.dtos.tenant.TenantResponse;
import bo.com.micrium.modulobase.modulos.administracion.mappers.TenantMapper;
import com.micrium.bd.access.jpa.modulo.administracion.models.Tenant;
import com.micrium.bd.access.jpa.modulo.administracion.repositories.ITenantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TenantService implements ITenantService {

    private final ITenantRepository repository;

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

    public TenantService(ITenantRepository repository) {
        this.repository = repository;
    }
}
