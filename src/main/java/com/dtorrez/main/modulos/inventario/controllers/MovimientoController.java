package com.dtorrez.main.modulos.inventario.controllers;

import com.dtorrez.main.common.response.ApiResponse;
import com.dtorrez.main.controllers.template.ICreateMethod;
import com.dtorrez.main.controllers.template.IListMethod;
import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.list.ListMovimientoProductoRequest;
import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.list.ListMovimientoProductoResponse;
import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoProductoRequest;
import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoResponse;
import com.dtorrez.main.modulos.inventario.services.movimiento.listar.IListarMovimientoProductoService;
import com.dtorrez.main.modulos.inventario.services.movimiento.registrar.IRegistrarMovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/movimientos")
public class MovimientoController implements
        IListMethod<ListMovimientoProductoRequest, ListMovimientoProductoResponse>,
        ICreateMethod<MovimientoProductoRequest, MovimientoResponse> {

    @Autowired
    private IListarMovimientoProductoService listService;

    @Autowired
    private IRegistrarMovimientoService service;

    @Override
    public ResponseEntity<ApiResponse<Page<ListMovimientoProductoResponse>>> list(
            String token,
            String tenantId,
            String ipClient,
            String form,
            ListMovimientoProductoRequest request,
            Pageable pageRequest) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.listService.listar(request, pageRequest),
                        "Movimiento listado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<MovimientoResponse>> create(
            String token,
            String tenantId,
            String ipClient,
            String form,
            MovimientoProductoRequest request
    ) {
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.service.registrar(request),
                        "Movimiento creado correctamente.")
                );
    }

}
