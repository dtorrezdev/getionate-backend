package bo.com.micrium.modulobase.modulos.promo.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.ICreateMethod;
import bo.com.micrium.modulobase.controllers.template.IDeleteMethod;
import bo.com.micrium.modulobase.controllers.template.IListMethod;
import bo.com.micrium.modulobase.controllers.template.IUpdateMethod;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.beneficio.BeneficioRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.beneficio.BeneficioResponse;
import bo.com.micrium.modulobase.modulos.promo.services.beneficio.IBeneficioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/beneficios")
public class BeneficioController implements IListMethod<BeneficioRequest, BeneficioResponse>,
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
    public ResponseEntity<ApiResponse<Page<BeneficioResponse>>> list(String token, String tenantId, String ipClient, String form, BeneficioRequest request, Pageable pageRequest) {
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
