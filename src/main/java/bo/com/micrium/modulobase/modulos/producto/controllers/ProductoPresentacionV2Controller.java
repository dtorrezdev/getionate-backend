package bo.com.micrium.modulobase.modulos.producto.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.IListController;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.list.*;
import bo.com.micrium.modulobase.modulos.producto.services.presentacion.list.IListProductoPresentacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/producto_presentacion_v2")
public class ProductoPresentacionV2Controller implements
        IListController<ListPresentacionRequest, ListPresentacionResponse> {

    @Autowired
    private IListProductoPresentacionService listService;

    @Override
    public ResponseEntity<ApiResponse<Page<ListPresentacionResponse>>> list(
            String token,
            String ipClient,
            String form,
            ListPresentacionRequest request,
            Pageable pageRequest
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.listService.execute(request, pageRequest),
                        "Se ha listado correctamente")
                );
    }
}
