package bo.com.micrium.modulobase.modulos.compra.services.recepcion.anular;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.AnularRecepcionProductoRequest;

public interface IAnularRecepcionProductoService {

    void execute(AnularRecepcionProductoRequest request);
}

