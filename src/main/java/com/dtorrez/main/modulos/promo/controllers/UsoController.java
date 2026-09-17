package com.dtorrez.main.modulos.promo.controllers;

import com.dtorrez.main.common.response.ApiResponse;
import com.dtorrez.main.controllers.template.ICreateMethod;
import com.dtorrez.main.controllers.template.IDeleteMethod;
import com.dtorrez.main.controllers.template.IListMethod;
import com.dtorrez.main.controllers.template.IUpdateMethod;
import com.dtorrez.main.modulos.promo.controllers.dtos.uso.UsoListRequest;
import com.dtorrez.main.modulos.promo.controllers.dtos.uso.UsoRequest;
import com.dtorrez.main.modulos.promo.controllers.dtos.uso.UsoResponse;
import com.dtorrez.main.modulos.promo.services.uso.IUsoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/promocion_usos")
public class UsoController implements IListMethod<UsoListRequest, UsoResponse>,
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
    public ResponseEntity<ApiResponse<Page<UsoResponse>>> list(
            String token,
            String tenantId,
            String ipClient,
            String form, UsoListRequest request,
            Pageable pageRequest
    ) {
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
