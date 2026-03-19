package bo.com.micrium.modulobase.modulos.ventas.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.ICrudControlerV2;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.VentaRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.VentaResponse;
import bo.com.micrium.modulobase.modulos.ventas.services.ICrearVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/ventas", produces = { MediaType.APPLICATION_JSON_VALUE })
public class VentaController implements
        ICrudControlerV2<VentaRequest, VentaResponse, String> {

    @Autowired
    private ICrearVentaService crearService;

    @Override
    public ResponseEntity<ApiResponse<Page<VentaResponse>>> list(
            String token,
            String ipClient,
            String form,
            Map<String, String> params,
            Pageable pageRequest
    ) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<VentaResponse>> get(
            String token,
            String ipClient,
            String form,
            String id) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<VentaResponse>> create(
            String token,
            String ipClient,
            String form,
            VentaRequest request
    ) {
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.crearService.execute(request),
                        "Se ha creado correctamente")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<VentaResponse>> update(String token, String ipClient, String form, VentaRequest request, String id) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(String token, String ipClient, String form, String id) {
        return null;
    }
}
