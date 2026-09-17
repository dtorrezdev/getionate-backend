package com.dtorrez.main.modulos.inventario.services.movimiento.listar;

import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.list.ListMovimientoProductoRequest;
import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.list.ListMovimientoProductoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IListarMovimientoProductoService {

    Page<ListMovimientoProductoResponse> listar(ListMovimientoProductoRequest request, Pageable page);
}
