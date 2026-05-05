package bo.com.micrium.modulobase.modulos.compra.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.ICreateMethod;
import bo.com.micrium.modulobase.controllers.template.IDeleteMethod;
import bo.com.micrium.modulobase.controllers.template.IListMethod;
import bo.com.micrium.modulobase.controllers.template.IUpdateMethod;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.proveedor.*;
import bo.com.micrium.modulobase.modulos.compra.services.proveedor.IProveedorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/proveedores")
public class ProveedorController implements IListMethod<ProveedorRequest, ProveedorResponse>,
        ICreateMethod<ProveedorRequest, ProveedorResponse>,
        IUpdateMethod<ProveedorRequest, ProveedorResponse, Long>,
        IDeleteMethod<ProveedorRequest>
{
    private final IProveedorService service;

    @Override
    public ResponseEntity<ApiResponse<Page<ProveedorResponse>>> list(
            String token,
            String ipClient,
            String form,
            ProveedorRequest request,
            Pageable pageRequest
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.list(request, pageRequest),
                        "Proveedores listado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<ProveedorResponse>> create(String token, String ipClient, String form, ProveedorRequest request) {
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.service.create(request),
                        "Proveedor creado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<ProveedorResponse>> update(String token, String ipClient, String form, ProveedorRequest request, Long id) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.update(request, id),
                        "Proveedor actualizado correctamente.")
                );
    }

    @Override
    public ResponseEntity<?> delete(
            String token,
            String ipClient,
            String form,
            ProveedorRequest request
    ) {
        this.service.delete(request.getId());
        return ResponseEntity.status(204).build();
    }

    public ProveedorController(IProveedorService service) {
        this.service = service;
    }
}

