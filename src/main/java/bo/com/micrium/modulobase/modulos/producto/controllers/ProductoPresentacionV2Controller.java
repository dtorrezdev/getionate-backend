package bo.com.micrium.modulobase.modulos.producto.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.ICreateMethod;
import bo.com.micrium.modulobase.controllers.template.IDeleteMethod;
import bo.com.micrium.modulobase.controllers.template.IGetMethod;
import bo.com.micrium.modulobase.controllers.template.IListMethod;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.CreateProductoPresentacionResponse;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.DeleteProductoRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionResponse;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.list.*;
import bo.com.micrium.modulobase.modulos.producto.services.presentacion.create.ICreateProductoPresentacionService;
import bo.com.micrium.modulobase.modulos.producto.services.presentacion.delete.IDeleteProductoPresentacionService;
import bo.com.micrium.modulobase.modulos.producto.services.presentacion.get.IGetProductoPresentacionService;
import bo.com.micrium.modulobase.modulos.producto.services.presentacion.list.IListProductoPresentacionService;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.get.GetVentaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/producto_presentacion_v2")
public class ProductoPresentacionV2Controller implements
        IListMethod<ListPresentacionRequest, ListPresentacionResponse>,
        IGetMethod<ProductoPresentacionResponse, Long>,
        ICreateMethod<ProductoPresentacionRequest, CreateProductoPresentacionResponse>,
        IDeleteMethod<DeleteProductoRequest> {

    private final IListProductoPresentacionService listService;
    private final ICreateProductoPresentacionService createService;
    private final IGetProductoPresentacionService getService;
    private final IDeleteProductoPresentacionService deleteService;

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
                        "Se ha obtenido correctamente")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<CreateProductoPresentacionResponse>> create(
            String token,
            String ipClient,
            String form,
            ProductoPresentacionRequest request
    ) {
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.createService.execute(request),
                        "Se ha creado correctamente")
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

    public ProductoPresentacionV2Controller(
            IListProductoPresentacionService listService,
            IGetProductoPresentacionService getService,
            ICreateProductoPresentacionService createService,
            IDeleteProductoPresentacionService deleteService
    ) {
        this.listService = listService;
        this.getService = getService;
        this.createService = createService;
        this.deleteService = deleteService;
    }
}
