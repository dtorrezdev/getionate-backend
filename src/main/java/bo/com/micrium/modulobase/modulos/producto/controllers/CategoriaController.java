package bo.com.micrium.modulobase.modulos.producto.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.ICreateMethod;
import bo.com.micrium.modulobase.controllers.template.IListMethod;
import bo.com.micrium.modulobase.controllers.template.IUpdateMethod;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.categoria.*;
import bo.com.micrium.modulobase.modulos.producto.services.categoria.ICategoriaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categoria")
public class CategoriaController implements IListMethod<CategoriaRequest, CategoriaResponse>,
        ICreateMethod<CategoriaRequest, CategoriaResponse>,
        IUpdateMethod<CategoriaRequest, CategoriaResponse, Long> {

    private final ICategoriaService service;

    @Override
    public ResponseEntity<ApiResponse<Page<CategoriaResponse>>> list(
            String token,
            String ipClient,
            String form,
            CategoriaRequest request,
            Pageable pageRequest
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.list(request, pageRequest),
                        "Se ha listado correctamente")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<CategoriaResponse>> create(String token, String ipClient, String form, CategoriaRequest request) {
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.service.create(request),
                        "Se ha creado correctamente")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<CategoriaResponse>> update(String token, String ipClient, String form, CategoriaRequest request, Long id) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.update(request, id),
                        "Se ha actualizado correctamente")
                );
    }

    public CategoriaController(ICategoriaService service) {
        this.service = service;
    }
}
