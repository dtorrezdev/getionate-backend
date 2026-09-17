package com.dtorrez.main.modulos.promo.controllers;

import com.dtorrez.main.common.response.ApiResponse;
import com.dtorrez.main.controllers.template.ICreateMethod;
import com.dtorrez.main.controllers.template.IDeleteMethod;
import com.dtorrez.main.controllers.template.IListMethod;
import com.dtorrez.main.controllers.template.IUpdateMethod;
import com.dtorrez.main.modulos.promo.controllers.dtos.beneficio.BeneficioListRequest;
import com.dtorrez.main.modulos.promo.controllers.dtos.beneficio.BeneficioListResponse;
import com.dtorrez.main.modulos.promo.controllers.dtos.beneficio.BeneficioRequest;
import com.dtorrez.main.modulos.promo.controllers.dtos.beneficio.BeneficioResponse;
import com.dtorrez.main.modulos.promo.services.beneficio.IBeneficioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/beneficios")
public class BeneficioController implements
        IListMethod<BeneficioListRequest, BeneficioListResponse>,
        ICreateMethod<BeneficioRequest, BeneficioResponse>,
        IUpdateMethod<BeneficioRequest, BeneficioResponse, Long>,
        IDeleteMethod<BeneficioRequest> {

    private final IBeneficioService service;

    @Override
    public ResponseEntity<ApiResponse<BeneficioResponse>> create(String token, String tenantId, String ipClient, String form, BeneficioRequest request) {
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.service.create(request),
                        "Beneficio creado correctamente.")
                );
    }

    @Override
    public ResponseEntity<?> delete(String token, String ipClient, String form, BeneficioRequest request) {
        return ResponseEntity.status(204).build();
    }

    @Override
    public ResponseEntity<ApiResponse<Page<BeneficioListResponse>>> list(
            String token,
            String tenantId,
            String ipClient,
            String form,
            BeneficioListRequest request,
            Pageable pageRequest
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.list(request, pageRequest),
                        "Beneficios listado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<BeneficioResponse>> update(String token, String ipClient, String form, BeneficioRequest request, Long id) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.update(request, id),
                        "Beneficio actualizado correctamente.")
                );
    }

    public BeneficioController(IBeneficioService service) {
        this.service = service;
    }
}
