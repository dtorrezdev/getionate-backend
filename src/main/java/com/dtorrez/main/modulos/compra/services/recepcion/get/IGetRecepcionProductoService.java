package com.dtorrez.main.modulos.compra.services.recepcion.get;

import com.dtorrez.main.modulos.compra.controllers.dtos.recepcion.get.GetRecepcionProductoResponse;

public interface IGetRecepcionProductoService {

    GetRecepcionProductoResponse execute(Long recepcionProductoId);
}

