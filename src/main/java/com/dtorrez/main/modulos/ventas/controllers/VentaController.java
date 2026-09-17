package com.dtorrez.main.modulos.ventas.controllers;

import com.dtorrez.main.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.*;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.*;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.get.GetNotaVentaResponse;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.get.GetVentaRecienteResponse;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.get.GetVentaResponse;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.list.*;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.*;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.update.VentaUpdateRequest;
import com.dtorrez.main.modulos.ventas.services.venta.anular.IAnularVentaService;
import com.dtorrez.main.modulos.ventas.services.venta.create.ICreateVentaService;
import com.dtorrez.main.modulos.ventas.services.venta.get.IGetNotaVentaService;
import com.dtorrez.main.modulos.ventas.services.venta.get.IGetVentaRecienteService;
import com.dtorrez.main.modulos.ventas.services.venta.get.IGetVentaService;
import com.dtorrez.main.modulos.ventas.services.venta.list.IListVentaService;

import com.dtorrez.main.modulos.ventas.services.venta.update.IUpdateVentaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ventas")
public class VentaController implements IListMethod<ListVentaRequest, ListVentaResponse>,
        IGetMethod<GetVentaResponse, Long>, ICreateMethod<VentaRequest, VentaResponse>,
        IUpdateMethod<VentaUpdateRequest,VentaResponse, Long>, IDeleteMethod<AnularVentaRequest>
{
    private final IListVentaService listService;
    private final IGetVentaService getService;
    private final ICreateVentaService crearService;
    private final IUpdateVentaService updateService;
    private final IAnularVentaService anularService;
    private final IGetNotaVentaService getNotaVentaService;
    private final IGetVentaRecienteService getVentaRecienteService;

    @Override
    public ResponseEntity<ApiResponse<Page<ListVentaResponse>>> list(
            String token,
            String tenantId,
            String ipClient,
            String form,
            ListVentaRequest params,
            Pageable pageRequest
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.listService.execute(params, pageRequest),
                        "Ventas listado correctamente.")
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
                        "Venta obtenido correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<VentaResponse>> create(
            String token,
            String tenantId,
            String ipClient,
            String form,
            VentaRequest request
    ) {
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.crearService.execute(request),
                        "Venta creado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<VentaResponse>> update(
            String token,
            String ipClient,
            String form,
            VentaUpdateRequest request,
            Long id
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.updateService.execute(request, id),
                        "Venta actualizado correctamente.")
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

    @GetMapping("/nota/{id}")
    public ResponseEntity<ApiResponse<GetNotaVentaResponse>> notaVenta(@PathVariable Long id) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.getNotaVentaService.execute(id),
                        "Nota Venta obtenida correctamente.")
                );
    }

    @GetMapping("/reciente")
    public ResponseEntity<ApiResponse<List<GetVentaRecienteResponse>>> recientes() {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.getVentaRecienteService.execute(),
                        "Ventas Recientes obtenida correctamente.")
                );
    }

    public VentaController(
            IListVentaService listService,
            IGetVentaService getService,
            ICreateVentaService crearService,
            IUpdateVentaService updateService,
            IAnularVentaService anularService,
            IGetNotaVentaService getNotaVentaService,
            IGetVentaRecienteService getVentaRecienteService
    ){
        this.listService = listService;
        this.getService = getService;
        this.crearService = crearService;
        this.updateService = updateService;
        this.anularService = anularService;
        this.getNotaVentaService = getNotaVentaService;
        this.getVentaRecienteService = getVentaRecienteService;
    }
}
