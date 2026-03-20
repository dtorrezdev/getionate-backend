package bo.com.micrium.modulobase.modulos.ventas.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.ICreateController;
import bo.com.micrium.modulobase.controllers.template.IDeleteController;
import bo.com.micrium.modulobase.controllers.template.IListController;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.AnularVentaRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.ListVentaRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.ListVentaResponse;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.VentaRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.VentaResponse;
import bo.com.micrium.modulobase.modulos.ventas.services.venta.IAnularVentaService;
import bo.com.micrium.modulobase.modulos.ventas.services.venta.ICrearVentaService;
import bo.com.micrium.modulobase.modulos.ventas.services.venta.IListVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/ventas", produces = { MediaType.APPLICATION_JSON_VALUE })
public class VentaController implements
        IListController<ListVentaRequest, ListVentaResponse>,
        ICreateController<VentaRequest, VentaResponse>,
        IDeleteController<AnularVentaRequest>
{
    @Autowired
    private IListVentaService listServie;

    @Autowired
    private ICrearVentaService crearService;

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
                        this.listServie.execute(params, pageRequest),
                        "Se ha listado correctamente")
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
