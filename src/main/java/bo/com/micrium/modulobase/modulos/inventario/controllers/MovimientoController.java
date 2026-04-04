package bo.com.micrium.modulobase.modulos.inventario.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.ICreateController;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.*;
import org.springframework.http.ResponseEntity;


public class MovimientoController implements
        ICreateController<MovimientoRequest, MovimientoResponse> {


    @Override
    public ResponseEntity<ApiResponse<MovimientoResponse>> create(String token, String ipClient, String form, MovimientoRequest request) {
        return null;
    }
}
