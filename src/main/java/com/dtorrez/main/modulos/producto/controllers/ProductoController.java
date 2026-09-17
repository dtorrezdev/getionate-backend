package com.dtorrez.main.modulos.producto.controllers;

import com.dtorrez.main.common.response.ApiResponse;
import com.dtorrez.main.controllers.template.ICrudMethods;
import com.dtorrez.main.modulos.producto.controllers.dtos.producto.ProductoListRequest;
import com.dtorrez.main.modulos.producto.controllers.dtos.producto.ProductoRequest;
import com.dtorrez.main.modulos.producto.controllers.dtos.producto.ProductoResponse;
import com.dtorrez.main.modulos.producto.services.producto.IProductoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/productos")
public class ProductoController implements
        ICrudMethods<ProductoRequest, ProductoResponse, Long> {

    private final IProductoService service;

    @Override
    public ResponseEntity<ApiResponse<Page<ProductoResponse>>> list(
            String token,
            String tenantId,
            String ipClient,
            String form,
            Map<String, String> params,
            Pageable pageRequest
    ) {
        final String codigo = params.get("codigo");
        final String nombre = params.get("nombre");
        final String descripcion = params.get("descripcion");
        final String categoria = params.get("categoria");

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok(
                        this.service.list(new ProductoListRequest(codigo, nombre, descripcion, categoria), pageRequest),
                        "Productos listado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<ProductoResponse>> get(String token, String ipClient, String form, Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok(
                        this.service.get(id),
                        "Producto obtenido correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<ProductoResponse>> create(
            String token,
            String tenantId,
            String ipClient,
            String form,
            ProductoRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(
                        this.service.create(request),
                        "Producto creado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<ProductoResponse>> update(
            String token,
            String ipClient,
            String form,
            ProductoRequest request,
            Long id
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.update(request, id),
                        "Producto actualizado correctamente.")
                );
    }

    @Override
    public ResponseEntity<?> delete(
            String token,
            String ipClient,
            String form,
            Long id
    ) {
        this.service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public ProductoController(IProductoService service) {
        this.service = service;
    }
}

