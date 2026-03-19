package bo.com.micrium.modulobase.modulos.producto.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.ICrudControlerV2;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.marca.MarcaRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.marca.MarcaResponse;
import bo.com.micrium.modulobase.modulos.producto.services.IMarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/marcas", produces = { MediaType.APPLICATION_JSON_VALUE })
public class MarcaController implements
        ICrudControlerV2<MarcaRequest, MarcaResponse, String> {

    @Autowired
    private IMarcaService service;

    @Override
    public ResponseEntity<ApiResponse<Page<MarcaResponse>>> list(
           String token,
           String ipClient,
           String form,
           Map<String, String> params,
           Pageable pageRequest
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.list(params, pageRequest),
                        "Se ha listado correctamente")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<MarcaResponse>> get(String token, String ipClient, String form, String id) {
        throw new RuntimeException("GET not support method /{id}" + id);
    }

    @Override
    public ResponseEntity<ApiResponse<MarcaResponse>> create(
            String token,
            String ipClient,
            String form,
            MarcaRequest request
    ) {
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.service.create(request),
                        "Se ha creado correctamente")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<MarcaResponse>> update(
            String token,
            String ipClient,
            String form,
            MarcaRequest request,
            String id
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.update(request, id),
                        "Se ha actualizado correctamente")
                );
    }

    @Override
    public ResponseEntity<?> delete(
            String token,
            String ipClient,
            String form,
            String id
    ) {
        this.service.delete(id);
        return ResponseEntity.status(204).build();
    }
}
