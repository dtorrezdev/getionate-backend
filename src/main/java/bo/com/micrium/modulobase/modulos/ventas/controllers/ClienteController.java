package bo.com.micrium.modulobase.modulos.ventas.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.IListController;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.cliente.ListClienteRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.cliente.ListClienteResponse;
import bo.com.micrium.modulobase.modulos.ventas.services.cliente.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clientes", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ClienteController implements
       IListController<ListClienteRequest, ListClienteResponse> {

    @Autowired
    private IClienteService service;

    @Override
    public ResponseEntity<ApiResponse<Page<ListClienteResponse>>> list(
            String token,
            String ipClient,
            String form,
            ListClienteRequest params,
            Pageable pageRequest) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.list(params, pageRequest),
                        "Se ha listado correctamente")
                );
    }
}
