package bo.com.micrium.modulobase.modulos.ventas.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.ICreateController;
import bo.com.micrium.modulobase.controllers.template.IDeleteController;
import bo.com.micrium.modulobase.controllers.template.IGetController;
import bo.com.micrium.modulobase.controllers.template.IListController;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.*;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.get.GetVentaResponse;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.list.*;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.*;
import bo.com.micrium.modulobase.modulos.ventas.services.venta.anular.IAnularVentaService;
import bo.com.micrium.modulobase.modulos.ventas.services.venta.create.ICreateVentaService;
import bo.com.micrium.modulobase.modulos.ventas.services.venta.get.IGetVentaService;
import bo.com.micrium.modulobase.modulos.ventas.services.venta.list.IListVentaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/ventas")
public class VentaController implements
        IListController<ListVentaRequest, ListVentaResponse>,
        IGetController<GetVentaResponse, Long>,
        ICreateController<VentaRequest, VentaResponse>,
        IDeleteController<AnularVentaRequest>
{
    @Autowired
    private IListVentaService listService;

    @Autowired
    private IGetVentaService getService;

    @Autowired
    private ICreateVentaService crearService;

    @Autowired
    private IAnularVentaService anularService;

    @Override
    public ResponseEntity<ApiResponse<Page<ListVentaResponse>>> list(
            String token,
            String ipClient,
            String form,
            ListVentaRequest params,
            Pageable pageRequest
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.listService.execute(params, pageRequest),
                        "Se ha listado correctamente")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<GetVentaResponse>> get(
            String token,
            String ipClient,
            String form,
            Long id) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.getService.execute(id),
                        "Se ha obtenido correctamente")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<VentaResponse>> create(
            String token,
            String ipClient,
            String form,
            VentaRequest request
    ) {
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.crearService.execute(request),
                        "Se ha creado correctamente")
                );
    }

    @Override
    public ResponseEntity<?> delete(
            String token,
            String ipClient,
            String form,
            AnularVentaRequest request) {
        this.anularService.execute(request);
        return ResponseEntity.status(204).build();
    }
}
