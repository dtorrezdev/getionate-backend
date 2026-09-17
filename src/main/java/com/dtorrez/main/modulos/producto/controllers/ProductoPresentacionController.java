package com.dtorrez.main.modulos.producto.controllers;

import com.dtorrez.main.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.*;
import com.dtorrez.main.modulos.producto.controllers.dtos.producto_presentacion.DeleteProductoRequest;
import com.dtorrez.main.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionRequest;
import com.dtorrez.main.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionResponse;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.list.*;
import com.dtorrez.main.modulos.producto.services.presentacion.create.ICreateProductoPresentacionService;
import com.dtorrez.main.modulos.producto.services.presentacion.delete.IDeleteProductoPresentacionService;
import com.dtorrez.main.modulos.producto.services.presentacion.get.IGetProductoPresentacionService;
import com.dtorrez.main.modulos.producto.services.presentacion.list.IListProductoPresentacionService;
import com.dtorrez.main.modulos.producto.services.presentacion.update.IUpdateProductoPresentacionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/producto_presentacion")
public class ProductoPresentacionController implements
        IListMethod<ListPresentacionRequest, ListPresentacionResponse>,
        IGetMethod<ProductoPresentacionResponse, Long>,
        ICreateMethod<ProductoPresentacionRequest, ProductoPresentacionResponse>,
        IUpdateMethod<ProductoPresentacionRequest, ProductoPresentacionResponse, Long>,
        IDeleteMethod<DeleteProductoRequest> {

    private final IListProductoPresentacionService listService;
    private final ICreateProductoPresentacionService createService;
    private final IUpdateProductoPresentacionService updateService;
    private final IGetProductoPresentacionService getService;
    private final IDeleteProductoPresentacionService deleteService;

    @Override
    public ResponseEntity<ApiResponse<Page<ListPresentacionResponse>>> list(
            String token,
            String tenantId,
            String ipClient,
            String form,
            ListPresentacionRequest request,
            Pageable pageRequest
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.listService.execute(request, pageRequest),
                        "Presentaciones listado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<ProductoPresentacionResponse>> get(
            String token,
            String ipClient,
            String form,
            Long id
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.getService.execute(id),
                        "Presentacion obtenida correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<ProductoPresentacionResponse>> create(
            String token,
            String tenantId,
            String ipClient,
            String form,
            ProductoPresentacionRequest request
    ) {
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.createService.execute(request),
                        "Presentacion creado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<ProductoPresentacionResponse>> update(
            String token,
            String ipClient,
            String form,
            ProductoPresentacionRequest request,
            Long id
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.updateService.execute(request, id),
                        "Presentacion actualizado correctamente.")
                );
    }

    @Override
    public ResponseEntity<?> delete(
            String token,
            String ipClient,
            String form,
            DeleteProductoRequest request
    ) {
        this.deleteService.execute(request);
        return ResponseEntity.status(204).build();
    }

    public ProductoPresentacionController(
            IListProductoPresentacionService listService,
            IGetProductoPresentacionService getService,
            ICreateProductoPresentacionService createService,
            IUpdateProductoPresentacionService updateService,
            IDeleteProductoPresentacionService deleteService
    ) {
        this.listService = listService;
        this.getService = getService;
        this.createService = createService;
        this.updateService = updateService;
        this.deleteService = deleteService;
    }
}
