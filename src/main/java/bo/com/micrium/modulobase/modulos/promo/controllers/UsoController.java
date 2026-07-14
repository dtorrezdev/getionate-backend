package bo.com.micrium.modulobase.modulos.promo.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.ICreateMethod;
import bo.com.micrium.modulobase.controllers.template.IDeleteMethod;
import bo.com.micrium.modulobase.controllers.template.IListMethod;
import bo.com.micrium.modulobase.controllers.template.IUpdateMethod;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.uso.UsoRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.uso.UsoResponse;
import bo.com.micrium.modulobase.modulos.promo.services.uso.IUsoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public class UsoController implements IListMethod<UsoRequest, UsoResponse>,
        ICreateMethod<UsoRequest, UsoResponse>,
        IUpdateMethod<UsoRequest, UsoResponse, Long>,
        IDeleteMethod<UsoRequest> {

    private final IUsoService service;

    public UsoController(IUsoService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<ApiResponse<UsoResponse>> create(String token, String tenantId, String ipClient, String form, UsoRequest request) {
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.service.create(request),
                        "Uso creado correctamente.")
                );
    }

    @Override
    public ResponseEntity<?> delete(String token, String ipClient, String form, UsoRequest request) {
        return ResponseEntity.status(204).build();
    }

    @Override
    public ResponseEntity<ApiResponse<Page<UsoResponse>>> list(String token, String tenantId, String ipClient, String form, UsoRequest request, Pageable pageRequest) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.list(request, pageRequest),
                        "Usos listado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<UsoResponse>> update(String token, String ipClient, String form, UsoRequest request, Long id) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.update(request, id),
                        "Uso actualizado correctamente.")
                );
    }
}
