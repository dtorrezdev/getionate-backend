package bo.com.micrium.modulobase.modulos.compra.services.compra.update;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.crear.CompraResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.update.CompraUpdateRequest;

public interface IUpdateCompraService {
    CompraResponse execute(CompraUpdateRequest request, Long id);
}

