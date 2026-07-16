package bo.com.micrium.modulobase.modulos.promo.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.ICreateMethod;
import bo.com.micrium.modulobase.controllers.template.IDeleteMethod;
import bo.com.micrium.modulobase.controllers.template.IListMethod;
import bo.com.micrium.modulobase.controllers.template.IUpdateMethod;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.target.TargetListRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.target.TargetRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.target.TargetResponse;
import bo.com.micrium.modulobase.modulos.promo.services.target.ITargetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/targets")
public class TargetController implements IListMethod<TargetListRequest, TargetResponse>,
        ICreateMethod<TargetRequest, TargetResponse>,
        IUpdateMethod<TargetRequest, TargetResponse, Long>,
        IDeleteMethod<TargetRequest> {

    private final ITargetService service;

    public TargetController(ITargetService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<ApiResponse<TargetResponse>> create(String token, String tenantId, String ipClient, String form, TargetRequest request) {
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.service.create(request),
                        "Target creado correctamente.")
                );
    }

    @Override
    public ResponseEntity<?> delete(String token, String ipClient, String form, TargetRequest request) {
        return ResponseEntity.status(204).build();
    }

    @Override
    public ResponseEntity<ApiResponse<Page<TargetResponse>>> list(String token, String tenantId, String ipClient, String form, TargetListRequest request, Pageable pageRequest) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.list(request, pageRequest),
                        "Targets listado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<TargetResponse>> update(String token, String ipClient, String form, TargetRequest request, Long id) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.update(request, id),
                        "Target actualizado correctamente.")
                );
    }
}
