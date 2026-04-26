package bo.com.micrium.modulobase.modulos.compra.services.recepcion.create;

import org.springframework.stereotype.Service;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.crear.RecepcionProductoRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.crear.RecepcionProductoResponse;

@Service
public class CreateRecepcionProductoServiceImpl implements ICreateRecepcionProductoService {

    @Override
    public RecepcionProductoResponse execute(RecepcionProductoRequest request) {
        // TODO: Implementar lógica de negocio
        return new RecepcionProductoResponse();
    }
}

