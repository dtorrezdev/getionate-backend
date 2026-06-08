package bo.com.micrium.modulobase.modulos.administracion.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.*;
import bo.com.micrium.modulobase.modulos.administracion.controllers.dtos.tenant.*;
import bo.com.micrium.modulobase.modulos.administracion.services.tenant.ITenantService;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tenants")
public class TenantController implements IListMethod<TenantRequest, TenantResponse>,
        ICreateMethod<TenantRequest, TenantResponse>,
        IGetMethod<TenantResponse, Long>,
        IUpdateMethod<TenantRequest, TenantResponse, Long>,
        IDeleteMethod<TenantRequest>
{
    private final ITenantService service;

    @Override
    public ResponseEntity<ApiResponse<Page<TenantResponse>>> list(
            String token,
            String tenantId,
            String ipClient,
            String form,
            TenantRequest request,
            Pageable pageRequest
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.list(request, pageRequest),
                        "Tenants listado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<TenantResponse>> get(
            String token,
            String ipClient,
            String form,
            Long id) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.get(id),
                        "Tenant obtenido correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<TenantResponse>> create(
          String token,
          String tenantId,
          String ipClient,
          String form,
          TenantRequest request
    ) {
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.service.create(request),
                        "Tenant creado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<TenantResponse>> update(String token, String ipClient, String form, TenantRequest request, Long id) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.update(request, id),
                        "Tenant actualizado correctamente.")
                );
    }

    @Override
    public ResponseEntity<?> delete(
            String token,
            String ipClient,
            String form,
            TenantRequest request
    ) {
        this.service.delete(request.getId());
        return ResponseEntity.status(204).build();
    }

    public TenantController(ITenantService service) {
        this.service = service;
    }
}
