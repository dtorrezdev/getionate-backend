package com.dtorrez.main.modulos.compra.services.recepcion.update;

import org.springframework.stereotype.Service;
import com.dtorrez.main.modulos.compra.controllers.dtos.recepcion.crear.RecepcionProductoResponse;
import com.dtorrez.main.modulos.compra.controllers.dtos.recepcion.update.RecepcionProductoUpdateRequest;

@Service
public class UpdateRecepcionProductoServiceImpl implements IUpdateRecepcionProductoService {

    @Override
    public RecepcionProductoResponse execute(RecepcionProductoUpdateRequest request, Long id) {
        // TODO: Implementar lógica de negocio
        return new RecepcionProductoResponse();
    }
}

