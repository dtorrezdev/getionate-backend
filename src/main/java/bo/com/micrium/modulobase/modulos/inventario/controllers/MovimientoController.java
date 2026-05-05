package bo.com.micrium.modulobase.modulos.inventario.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.ICreateMethod;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoResponse;
import bo.com.micrium.modulobase.modulos.inventario.services.movimiento.ICreateMovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/movimientos")
public class MovimientoController implements
        ICreateMethod<MovimientoRequest, MovimientoResponse> {

    @Autowired
    private ICreateMovimientoService createService;

    @Override
    public ResponseEntity<ApiResponse<MovimientoResponse>> create(
            String token,
            String ipClient,
            String form,
            MovimientoRequest request
    ) {
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.createService.execute(request),
                        "Movimiento creado correctamente.")
                );
    }


}
