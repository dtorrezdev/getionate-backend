package bo.com.micrium.modulobase.modulos.compra.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.*;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.*;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.crear.*;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.get.GetOrdenCompraResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.list.*;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.update.ChangeStateRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.update.CompraUpdateRequest;
import bo.com.micrium.modulobase.modulos.compra.services.compra.anular.IAnularCompraService;
import bo.com.micrium.modulobase.modulos.compra.services.compra.create.ICreateCompraService;
import bo.com.micrium.modulobase.modulos.compra.services.compra.get.IGetCompraService;
import bo.com.micrium.modulobase.modulos.compra.services.compra.list.IListCompraService;
import bo.com.micrium.modulobase.modulos.compra.services.compra.update.IUpdateCompraService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/compras/orden")
public class OrdenCompraController implements IListMethod<OrdenCompraRequest, OrdenCompraResponse>,
        IGetMethod<GetOrdenCompraResponse, Long>, ICreateMethod<CompraRequest, CompraResponse>,
        IUpdateMethod<CompraUpdateRequest, CompraResponse, Long>, IDeleteMethod<AnularCompraRequest>
{
    private final IListCompraService listService;
    private final IGetCompraService getService;
    private final ICreateCompraService crearService;
    private final IUpdateCompraService updateService;
    private final IAnularCompraService anularService;

    @Override
    public ResponseEntity<ApiResponse<Page<OrdenCompraResponse>>> list(
            String token,
            String tenantId,
            String ipClient,
            String form,
            OrdenCompraRequest params,
            Pageable pageRequest
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.listService.listOrdenCompra(params, pageRequest),
                        "Orden de Compras listado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<GetOrdenCompraResponse>> get(
            String token,
            String ipClient,
            String form,
            Long id) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.getService.getOrden(id),
                        "Orden Compra obtenido correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<CompraResponse>> create(
            String token,
            String tenantId,
            String ipClient,
            String form,
            CompraRequest request
    ) {
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.crearService.createOrden(request),
                        "Orden Compra registrado correctamente."
                ));
    }

    @Override
    public ResponseEntity<ApiResponse<CompraResponse>> update(
            String token,
            String ipClient,
            String form,
            CompraUpdateRequest request,
            Long id
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.updateService.updateOrder(request, id),
                        "Orden Compra actualizado correctamente.")
                );
    }

    @Override
    public ResponseEntity<?> delete(
            String token,
            String ipClient,
            String form,
            AnularCompraRequest request) {
        this.anularService.execute(request);
        return ResponseEntity.status(204).build();
    }

    @PostMapping("/enviar")
    public ResponseEntity<?> enviarOrden(@RequestBody ChangeStateRequest request) {
        this.updateService.cambiarEstado(request);
        return ResponseEntity.status(204).build();
    }

    public OrdenCompraController(
            IListCompraService listService,
            IGetCompraService getService,
            ICreateCompraService crearService,
            IUpdateCompraService updateService,
            IAnularCompraService anularService
    ){
        this.listService = listService;
        this.getService = getService;
        this.crearService = crearService;
        this.updateService = updateService;
        this.anularService = anularService;
    }

}

