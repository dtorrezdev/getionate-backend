package bo.com.micrium.modulobase.modulos.compra.services.recepcion.update;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.crear.RecepcionProductoResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.update.RecepcionProductoUpdateRequest;

public interface IUpdateRecepcionProductoService {
    RecepcionProductoResponse execute(RecepcionProductoUpdateRequest request, Long id);
}

