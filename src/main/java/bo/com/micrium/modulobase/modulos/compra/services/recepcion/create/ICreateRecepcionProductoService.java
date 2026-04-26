package bo.com.micrium.modulobase.modulos.compra.services.recepcion.create;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.crear.RecepcionProductoRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.crear.RecepcionProductoResponse;

public interface ICreateRecepcionProductoService {

    RecepcionProductoResponse execute(RecepcionProductoRequest request);

}

