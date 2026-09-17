package com.dtorrez.main.modulos.compra.services.compra.update;

import com.dtorrez.main.modulos.compra.controllers.dtos.orden_compra.crear.CompraResponse;
import com.dtorrez.main.modulos.compra.controllers.dtos.orden_compra.update.ChangeStateRequest;
import com.dtorrez.main.modulos.compra.controllers.dtos.orden_compra.update.CompraUpdateRequest;

public interface IUpdateCompraService {
    CompraResponse updateOrder(CompraUpdateRequest request, Long id);

    CompraResponse updateSolicitud(CompraUpdateRequest request, Long id);

    void cambiarEstado(ChangeStateRequest request);
}

