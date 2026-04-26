package bo.com.micrium.modulobase.modulos.compra.services.recepcion.get;

import org.springframework.stereotype.Service;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.get.GetRecepcionProductoResponse;

@Service
public class GetRecepcionProductoServiceImpl implements IGetRecepcionProductoService {

    @Override
    public GetRecepcionProductoResponse execute(Long recepcionProductoId) {
        // TODO: Implementar lógica de negocio
        return new GetRecepcionProductoResponse();
    }
}

