package com.dtorrez.main.modulos.compra.services.recepcion.update;

import com.dtorrez.main.modulos.compra.controllers.dtos.recepcion.crear.RecepcionProductoResponse;
import com.dtorrez.main.modulos.compra.controllers.dtos.recepcion.update.RecepcionProductoUpdateRequest;

public interface IUpdateRecepcionProductoService {
    RecepcionProductoResponse execute(RecepcionProductoUpdateRequest request, Long id);
}

