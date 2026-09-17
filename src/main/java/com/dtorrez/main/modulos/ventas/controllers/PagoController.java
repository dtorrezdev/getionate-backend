package com.dtorrez.main.modulos.ventas.controllers;

import com.dtorrez.main.common.response.ApiResponse;
import com.dtorrez.main.controllers.template.ICreateMethod;
import com.dtorrez.main.modulos.ventas.controllers.dtos.pago.PagoRequest;
import com.dtorrez.main.modulos.ventas.controllers.dtos.pago.PagoResponse;

import com.dtorrez.main.modulos.ventas.services.pago.IPagoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/pagos")
public class PagoController implements
        ICreateMethod<PagoRequest, PagoResponse> {

    @Autowired
    private IPagoService service;

    @Override
    public ResponseEntity<ApiResponse<PagoResponse>> create(
            String token,
            String tenantId,
            String ipClient,
            String form,
            PagoRequest request
    ) {
        // No esta validado
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.service.save(request),
                        "Pagos creado correctamente.")
                );
    }
}
