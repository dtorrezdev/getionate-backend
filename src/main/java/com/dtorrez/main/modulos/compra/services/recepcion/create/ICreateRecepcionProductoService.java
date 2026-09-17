package com.dtorrez.main.modulos.compra.services.recepcion.create;

import com.dtorrez.main.modulos.compra.controllers.dtos.recepcion.crear.RecepcionProductoRequest;
import com.dtorrez.main.modulos.compra.controllers.dtos.recepcion.crear.RecepcionProductoResponse;

public interface ICreateRecepcionProductoService {

    RecepcionProductoResponse execute(RecepcionProductoRequest request);

}

