package com.dtorrez.main.modulos.compra.services.compra.get;

import com.dtorrez.main.modulos.compra.controllers.dtos.orden_compra.get.GetOrdenCompraResponse;
import com.dtorrez.main.modulos.compra.controllers.dtos.solicitud.GetSolicitudCompraResponse;

public interface IGetCompraService {

    GetOrdenCompraResponse getOrden(Long compraId);

    GetSolicitudCompraResponse getSolicitud(Long compraId);
}

