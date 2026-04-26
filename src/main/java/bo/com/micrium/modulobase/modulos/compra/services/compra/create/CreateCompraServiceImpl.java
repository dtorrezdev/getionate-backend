package bo.com.micrium.modulobase.modulos.compra.services.compra.create;

import org.springframework.stereotype.Service;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.crear.CompraRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.crear.CompraResponse;

@Service
public class CreateCompraServiceImpl implements ICreateCompraService {

    @Override
    public CompraResponse execute(CompraRequest request) {
        // TODO: Implementar lógica de negocio
        return new CompraResponse();
    }
}

