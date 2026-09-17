package com.dtorrez.main.modulos.compra.services.recepcion.list;

import com.dtorrez.main.modulos.compra.controllers.dtos.recepcion.list.ListRecepcionProductoRequest;
import com.dtorrez.main.modulos.compra.controllers.dtos.recepcion.list.ListRecepcionProductoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IListRecepcionProductoService {

    Page<ListRecepcionProductoResponse> execute(ListRecepcionProductoRequest params, Pageable page);

}

