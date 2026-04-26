package bo.com.micrium.modulobase.modulos.compra.services.compra.get;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.get.GetCompraResponse;

public interface IGetCompraService {

    GetCompraResponse execute(Long compraId);
}

