package com.dtorrez.main.modulos.inventario.controllers;


import com.dtorrez.main.common.response.ApiResponse;
import com.dtorrez.main.controllers.template.ICreateMethod;
import com.dtorrez.main.controllers.template.IDeleteMethod;
import com.dtorrez.main.controllers.template.IListMethod;
import com.dtorrez.main.controllers.template.IUpdateMethod;
import com.dtorrez.main.modulos.inventario.controllers.dtos.ubicacion_stock.UbicacionStockRequest;
import com.dtorrez.main.modulos.inventario.controllers.dtos.ubicacion_stock.UbicacionStockResponse;
import com.dtorrez.main.modulos.inventario.services.ubicacion_stock.IUbicacionStockService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ubicacion_stock")
public class UbicacionStockController implements
        IListMethod<UbicacionStockRequest, UbicacionStockResponse>,
        ICreateMethod<UbicacionStockRequest, UbicacionStockResponse>,
        IUpdateMethod<UbicacionStockRequest,UbicacionStockResponse, Long>,
        IDeleteMethod<UbicacionStockResponse>
{

    private final IUbicacionStockService service;

    @Override
    public ResponseEntity<ApiResponse<Page<UbicacionStockResponse>>> list(
            String token,
            String tenantId,
            String ipClient,
            String form,
            UbicacionStockRequest request,
            Pageable pageRequest
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.list(request, pageRequest),
                        "Ubicacion Stock listado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<UbicacionStockResponse>> create(
            String token,
            String tenantId,
            String ipClient,
            String form,
            UbicacionStockRequest request
    ) {
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.service.create(request),
                        "Ubicacion Stock creado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<UbicacionStockResponse>> update(String token, String ipClient, String form, UbicacionStockRequest request, Long id) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.update(request, id),
                        "Ubicacion Stock actaulizado correctamente.")
                );
    }

    @Override
    public ResponseEntity<?> delete(String token, String ipClient, String form, UbicacionStockResponse request) {
        this.service.delete(request.getId());
        return ResponseEntity.status(204).build();
    }

    public UbicacionStockController(IUbicacionStockService service) {
        this.service = service;
    }
}
