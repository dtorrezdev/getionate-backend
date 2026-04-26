package bo.com.micrium.modulobase.modulos.compra.services.compra.get;

import org.springframework.stereotype.Service;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.get.GetCompraResponse;

@Service
public class GetCompraServiceImpl implements IGetCompraService {

    @Override
    public GetCompraResponse execute(Long compraId) {
        // TODO: Implementar lógica de negocio
        return new GetCompraResponse();
    }
}

