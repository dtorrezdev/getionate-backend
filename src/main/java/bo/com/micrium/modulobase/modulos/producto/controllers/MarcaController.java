package bo.com.micrium.modulobase.modulos.producto.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.ICrudMethods;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.marca.MarcaRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.marca.MarcaResponse;
import bo.com.micrium.modulobase.modulos.producto.services.marca.IMarcaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/marcas")
public class MarcaController implements
        ICrudMethods<MarcaRequest, MarcaResponse, String> {

    private final IMarcaService service;

    @Override
    public ResponseEntity<ApiResponse<Page<MarcaResponse>>> list(
           String token,
           String ipClient,
           String form,
           Map<String, String> params,
           Pageable pageRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.ok(
                        this.service.list(params, pageRequest),
                        "Marcas listado correctamente.")
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
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(
                        this.service.create(request),
                        "Marca creado correctamente.")
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
                        "Marca actualizado correctamente.")
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
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    public MarcaController(IMarcaService service) {
        this.service = service;
    }
}
