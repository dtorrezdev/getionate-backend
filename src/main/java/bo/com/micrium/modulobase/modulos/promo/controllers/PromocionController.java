package bo.com.micrium.modulobase.modulos.promo.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.ICreateMethod;
import bo.com.micrium.modulobase.controllers.template.IDeleteMethod;
import bo.com.micrium.modulobase.controllers.template.IListMethod;
import bo.com.micrium.modulobase.controllers.template.IUpdateMethod;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.promocion.PromocionRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.promocion.PromocionResponse;
import bo.com.micrium.modulobase.modulos.promo.services.promocion.IPromocionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/promociones")
public class PromocionController implements IListMethod<PromocionRequest, PromocionResponse>,
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
    public ResponseEntity<ApiResponse<Page<PromocionResponse>>> list(String token, String tenantId, String ipClient, String form, PromocionRequest request, Pageable pageRequest) {
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
