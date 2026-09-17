package com.dtorrez.main.modulos.compra.services.recepcion.anular;

import com.dtorrez.main.modulos.compra.controllers.dtos.recepcion.AnularRecepcionProductoRequest;

public interface IAnularRecepcionProductoService {

    void execute(AnularRecepcionProductoRequest request);
}

