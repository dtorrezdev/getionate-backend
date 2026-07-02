package bo.com.micrium.modulobase.modulos.compra.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.*;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.AnularCompraRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.crear.CompraRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.crear.CompraResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.get.GetOrdenCompraResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.update.CompraUpdateRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.solicitud.GetSolicitudCompraResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.solicitud.SolicitudCompraRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.solicitud.SolicitudCompraResponse;
import bo.com.micrium.modulobase.modulos.compra.services.compra.anular.IAnularCompraService;
import bo.com.micrium.modulobase.modulos.compra.services.compra.create.ICreateCompraService;
import bo.com.micrium.modulobase.modulos.compra.services.compra.get.IGetCompraService;
import bo.com.micrium.modulobase.modulos.compra.services.compra.list.IListCompraService;
import bo.com.micrium.modulobase.modulos.compra.services.compra.update.IUpdateCompraService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/compras/solicitud")
public class SolicitudCompraController implements IListMethod<SolicitudCompraRequest, SolicitudCompraResponse>,
        IGetMethod<GetSolicitudCompraResponse, Long>, ICreateMethod<CompraRequest, CompraResponse>,
        IUpdateMethod<CompraUpdateRequest, CompraResponse, Long>, IDeleteMethod<AnularCompraRequest>
{
    private final IListCompraService listService;
    private final IGetCompraService getService;
    private final ICreateCompraService crearService;
    private final IUpdateCompraService updateService;
    private final IAnularCompraService anularService;

    @Override
    public ResponseEntity<ApiResponse<Page<SolicitudCompraResponse>>> list(
            String token,
            String tenantId,
            String ipClient,
            String form,
            SolicitudCompraRequest params,
            Pageable pageRequest
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.listService.listSolicitudCompra(params, pageRequest),
                        "Solicitud Compras listado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<GetSolicitudCompraResponse>> get(
            String token,
            String ipClient,
            String form,
            Long id) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.getService.getSolicitud(id),
                        "Solicitud Compra obtenido correctamente.")
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
                        this.crearService.createSolicitud(request),
                        "Solicitud Compra registrado correctamente."
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
                        this.updateService.execute(request, id),
                        "Compra actualizado correctamente.")
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

    public SolicitudCompraController(
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

