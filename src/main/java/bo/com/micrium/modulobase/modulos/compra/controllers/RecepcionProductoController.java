package bo.com.micrium.modulobase.modulos.compra.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.*;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.*;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.crear.*;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.get.GetRecepcionProductoResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.list.*;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.update.RecepcionProductoUpdateRequest;
import bo.com.micrium.modulobase.modulos.compra.services.recepcion.anular.IAnularRecepcionProductoService;
import bo.com.micrium.modulobase.modulos.compra.services.recepcion.create.ICreateRecepcionProductoService;
import bo.com.micrium.modulobase.modulos.compra.services.recepcion.get.IGetRecepcionProductoService;
import bo.com.micrium.modulobase.modulos.compra.services.recepcion.list.IListRecepcionProductoService;
import bo.com.micrium.modulobase.modulos.compra.services.recepcion.update.IUpdateRecepcionProductoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/recepciones")
public class RecepcionProductoController implements IListMethod<ListRecepcionProductoRequest, ListRecepcionProductoResponse>,
        IGetMethod<GetRecepcionProductoResponse, Long>, ICreateMethod<RecepcionProductoRequest, RecepcionProductoResponse>,
        IUpdateMethod<RecepcionProductoUpdateRequest, RecepcionProductoResponse, Long>, IDeleteMethod<AnularRecepcionProductoRequest>
{
    private final IListRecepcionProductoService listService;
    private final IGetRecepcionProductoService getService;
    private final ICreateRecepcionProductoService crearService;
    private final IUpdateRecepcionProductoService updateService;
    private final IAnularRecepcionProductoService anularService;

    @Override
    public ResponseEntity<ApiResponse<Page<ListRecepcionProductoResponse>>> list(
            String token,
            String tenantId,
            String ipClient,
            String form,
            ListRecepcionProductoRequest params,
            Pageable pageRequest
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.listService.execute(params, pageRequest),
                        "Recepcion Productos listado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<GetRecepcionProductoResponse>> get(
            String token,
            String ipClient,
            String form,
            Long id) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.getService.execute(id),
                        "Recepcion Producto obtenido correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<RecepcionProductoResponse>> create(
            String token,
            String tenantId,
            String ipClient,
            String form,
            RecepcionProductoRequest request
    ) {
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.crearService.execute(request),
                        "Recepcion Producto creado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<RecepcionProductoResponse>> update(
            String token,
            String ipClient,
            String form,
            RecepcionProductoUpdateRequest request,
            Long id
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.updateService.execute(request, id),
                        "Recepcion Producto actualizado correctamente.")
                );
    }

    @Override
    public ResponseEntity<?> delete(
            String token,
            String ipClient,
            String form,
            AnularRecepcionProductoRequest request) {
        this.anularService.execute(request);
        return ResponseEntity.status(204).build();
    }

    public RecepcionProductoController(
            IListRecepcionProductoService listService,
            IGetRecepcionProductoService getService,
            ICreateRecepcionProductoService crearService,
            IUpdateRecepcionProductoService updateService,
            IAnularRecepcionProductoService anularService
    ){
        this.listService = listService;
        this.getService = getService;
        this.crearService = crearService;
        this.updateService = updateService;
        this.anularService = anularService;
    }
}

