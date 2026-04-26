package bo.com.micrium.modulobase.modulos.compra.services.recepcion.get;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.get.GetRecepcionProductoResponse;

public interface IGetRecepcionProductoService {

    GetRecepcionProductoResponse execute(Long recepcionProductoId);
}

