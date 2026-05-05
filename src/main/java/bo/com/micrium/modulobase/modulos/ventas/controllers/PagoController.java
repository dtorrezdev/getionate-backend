package bo.com.micrium.modulobase.modulos.ventas.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.ICreateMethod;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.pago.PagoRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.pago.PagoResponse;

import bo.com.micrium.modulobase.modulos.ventas.services.pago.IPagoService;

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
