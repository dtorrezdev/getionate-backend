package com.dtorrez.main.modulos.compra.services.compra.list;

import com.dtorrez.main.modulos.compra.controllers.dtos.orden_compra.list.OrdenCompraRequest;
import com.dtorrez.main.modulos.compra.controllers.dtos.orden_compra.list.OrdenCompraResponse;
import com.dtorrez.main.modulos.compra.controllers.dtos.solicitud.SolicitudCompraRequest;
import com.dtorrez.main.modulos.compra.controllers.dtos.solicitud.SolicitudCompraResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IListCompraService {

    Page<OrdenCompraResponse> listOrdenCompra(OrdenCompraRequest params, Pageable page);


    Page<SolicitudCompraResponse> listSolicitudCompra(SolicitudCompraRequest params, Pageable page);

}

