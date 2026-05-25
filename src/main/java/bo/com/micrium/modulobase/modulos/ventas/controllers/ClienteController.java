package bo.com.micrium.modulobase.modulos.ventas.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.ICreateMethod;
import bo.com.micrium.modulobase.controllers.template.IDeleteMethod;
import bo.com.micrium.modulobase.controllers.template.IListMethod;
import bo.com.micrium.modulobase.controllers.template.IUpdateMethod;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.cliente.ClienteRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.cliente.ListClienteRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.cliente.ClienteResponse;
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
        IListMethod<ListClienteRequest, ClienteResponse>,
        ICreateMethod<ClienteRequest, ClienteResponse>,
        IUpdateMethod<ClienteRequest,ClienteResponse, Long>, IDeleteMethod<ClienteRequest>
{
    private final IClienteService service;

    @Override
    public ResponseEntity<ApiResponse<Page<ClienteResponse>>> list(
            String token,
            String tenantId,
            String ipClient,
            String form,
            ListClienteRequest params,
            Pageable pageRequest) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.list(params, pageRequest),
                        "Clientes listado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<ClienteResponse>> create(
            String token,
            String tenantId,
            String ipClient,
            String form,
            ClienteRequest request
    ) {
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.service.create(request),
                        "Cliente creado correctamente.")
                );
    }

    @Override
    public ResponseEntity<?> delete(String token, String ipClient, String form, ClienteRequest request) {
        this.service.delete(request.getId());
        return ResponseEntity.status(204).build();
    }

    @Override
    public ResponseEntity<ApiResponse<ClienteResponse>> update(String token, String ipClient, String form, ClienteRequest request, Long id) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.update(request, id),
                        "Cliente actualizado correctamente.")
                );
    }

    public ClienteController(IClienteService service) {
        this.service = service;
    }
}
