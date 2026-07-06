package bo.com.micrium.modulobase.modulos.compra.services.compra.update;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.crear.CompraResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.update.ChangeStateRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.update.CompraUpdateRequest;

public interface IUpdateCompraService {
    CompraResponse updateOrder(CompraUpdateRequest request, Long id);

    CompraResponse updateSolicitud(CompraUpdateRequest request, Long id);

    void cambiarEstado(ChangeStateRequest request);
}

