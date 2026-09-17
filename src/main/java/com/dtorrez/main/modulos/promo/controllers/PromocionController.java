package com.dtorrez.main.modulos.promo.controllers;

import com.dtorrez.main.common.response.ApiResponse;
import com.dtorrez.main.controllers.template.ICreateMethod;
import com.dtorrez.main.controllers.template.IDeleteMethod;
import com.dtorrez.main.controllers.template.IListMethod;
import com.dtorrez.main.controllers.template.IUpdateMethod;
import com.dtorrez.main.modulos.promo.controllers.dtos.promocion.PromocionRequest;
import com.dtorrez.main.modulos.promo.controllers.dtos.promocion.PromocionListRequest;
import com.dtorrez.main.modulos.promo.controllers.dtos.promocion.PromocionResponse;
import com.dtorrez.main.modulos.promo.services.promocion.IPromocionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/promociones")
public class PromocionController implements IListMethod<PromocionListRequest, PromocionResponse>,
        ICreateMethod<PromocionRequest, PromocionResponse>,
        IUpdateMethod<PromocionRequest, PromocionResponse, Long>,
        IDeleteMethod<PromocionRequest> {

    private final IPromocionService service;

    @Override
    public ResponseEntity<ApiResponse<PromocionResponse>> create(String token, String tenantId, String ipClient, String form, PromocionRequest request) {
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.service.create(request),
                        "Promocion creado correctamente.")
                );
    }

    @Override
    public ResponseEntity<?> delete(String token, String ipClient, String form, PromocionRequest request) {
        return ResponseEntity.status(204).build();
    }

    @Override
    public ResponseEntity<ApiResponse<Page<PromocionResponse>>> list(String token, String tenantId, String ipClient, String form, PromocionListRequest request, Pageable pageRequest) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.list(request, pageRequest),
                        "Promociones listado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<PromocionResponse>> update(String token, String ipClient, String form, PromocionRequest request, Long id) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.update(request, id),
                        "Promocion actualizado correctamente.")
                );

    }

    public PromocionController(IPromocionService service) {
        this.service = service;
    }
}
