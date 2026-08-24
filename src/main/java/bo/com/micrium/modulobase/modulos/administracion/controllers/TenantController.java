package bo.com.micrium.modulobase.modulos.administracion.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.*;
import bo.com.micrium.modulobase.modulos.administracion.controllers.dtos.tenant.*;
import bo.com.micrium.modulobase.modulos.administracion.services.tenant.ITenantService;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionResponse;
import bo.com.micrium.modulobase.security.utils.JwtTokenUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tenants")
public class TenantController implements IListMethod<TenantRequest, TenantResponse>,
        ICreateMethod<TenantRequest, TenantResponse>,
//        IGetMethod<TenantResponse, Long>,
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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TenantResponse>> get(
            @RequestHeader(value = JwtTokenUtil.IP_CLIENT, required = false) String ipClient,
            @RequestHeader(value = JwtTokenUtil.ROUTE, defaultValue = "/local-test") String form,
            @PathVariable Long id
            ) {
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
