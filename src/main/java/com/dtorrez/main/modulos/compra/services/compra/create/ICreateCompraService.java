package com.dtorrez.main.modulos.compra.services.compra.create;

import com.dtorrez.main.modulos.compra.controllers.dtos.orden_compra.crear.CompraRequest;
import com.dtorrez.main.modulos.compra.controllers.dtos.orden_compra.crear.CompraResponse;

public interface ICreateCompraService {

    CompraResponse createOrden(CompraRequest request);

    CompraResponse createSolicitud(CompraRequest request);

}
