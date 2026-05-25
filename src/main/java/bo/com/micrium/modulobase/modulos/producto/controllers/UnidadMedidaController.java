package bo.com.micrium.modulobase.modulos.producto.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.*;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.unidad_medida.UnidadMedidaResponse;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.unidad_medida.UnidadMedidaRequest;
import bo.com.micrium.modulobase.modulos.producto.services.unidad_medida.IUnidadMedidaService;
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
        IListMethod<UnidadMedidaRequest, UnidadMedidaResponse>,
        ICreateMethod<UnidadMedidaRequest, UnidadMedidaResponse>,
        IUpdateMethod<UnidadMedidaRequest,UnidadMedidaResponse, Long>,
        IDeleteMethod<UnidadMedidaRequest>
{
    private final IUnidadMedidaService service;

    @Override
    public ResponseEntity<ApiResponse<Page<UnidadMedidaResponse>>> list(
            String token,
            String tenantId,
            String ipClient,
            String form,
            UnidadMedidaRequest request,
            Pageable pageRequest
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.list(request, pageRequest),
                        "Unidad Medidas listado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<UnidadMedidaResponse>> create(String token, String tenantId, String ipClient, String form, UnidadMedidaRequest request) {
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.service.create(request),
                        "Unidad de Medida creado correctamente.")
                );
    }

    @Override
    public ResponseEntity<?> delete(String token, String ipClient, String form, UnidadMedidaRequest request) {
        this.service.delete(request.getId());
        return ResponseEntity.status(204).build();
    }

    @Override
    public ResponseEntity<ApiResponse<UnidadMedidaResponse>> update(String token, String ipClient, String form, UnidadMedidaRequest request, Long id) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.update(request, id),
                        "Unidad de Medida actualizado correctamente.")
                );
    }

    public UnidadMedidaController(IUnidadMedidaService service) {
        this.service = service;
    }
}
