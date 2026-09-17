package com.dtorrez.main.modulos.administracion.services.tenant;

import com.dtorrez.main.modulos.administracion.controllers.dtos.tenant.TenantRequest;
import com.dtorrez.main.modulos.administracion.controllers.dtos.tenant.TenantResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITenantService {

    Page<TenantResponse> list(TenantRequest params, Pageable pageable);
    TenantResponse get(Long id);
    TenantResponse create(TenantRequest request);
    TenantResponse update(TenantRequest request, Long id);
    void delete(String id);
}
