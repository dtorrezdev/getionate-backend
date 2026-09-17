package com.dtorrez.main.modulos.compra.services.compra.anular;

import com.dtorrez.main.modulos.compra.controllers.dtos.orden_compra.AnularCompraRequest;

public interface IAnularCompraService {

    void execute(AnularCompraRequest request);
}

