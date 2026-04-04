package bo.com.micrium.modulobase.modulos.producto.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.ICrudControlerV2;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.unidad_medida.UnidadMedidaResponse;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.unidad_medida.UnidadMedidaRequest;
import bo.com.micrium.modulobase.modulos.producto.services.IUnidadMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/unidades_medidas")
public class UnidadMedidaController implements
        ICrudControlerV2<UnidadMedidaRequest, UnidadMedidaResponse, String> {

    @Autowired
    private IUnidadMedidaService service;

    @Override
    public ResponseEntity<ApiResponse<Page<UnidadMedidaResponse>>> list(String token, String ipClient, String form, Map<String, String> params, Pageable pageRequest) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.list(params, pageRequest),
                        "Se ha listado correctamente")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<UnidadMedidaResponse>> get(String token, String ipClient, String form, String id) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<UnidadMedidaResponse>> create(String token, String ipClient, String form, UnidadMedidaRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<UnidadMedidaResponse>> update(String token, String ipClient, String form, UnidadMedidaRequest request, String id) {
        return null;
    }

    @Override
    public ResponseEntity<?> delete(String token, String ipClient, String form, String id) {
        return null;
    }
}
