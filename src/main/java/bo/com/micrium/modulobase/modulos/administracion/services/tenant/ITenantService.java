package bo.com.micrium.modulobase.modulos.administracion.services.tenant;

import bo.com.micrium.modulobase.modulos.administracion.controllers.dtos.tenant.TenantRequest;
import bo.com.micrium.modulobase.modulos.administracion.controllers.dtos.tenant.TenantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITenantService {

    Page<TenantResponse> list(TenantRequest params, Pageable pageable);
    TenantResponse get(Long id);
    TenantResponse create(TenantRequest request);
    TenantResponse update(TenantRequest request, Long id);
    void delete(String id);
}
