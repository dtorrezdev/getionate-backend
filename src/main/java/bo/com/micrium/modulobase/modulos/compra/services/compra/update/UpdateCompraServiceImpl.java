package bo.com.micrium.modulobase.modulos.compra.services.compra.update;

import org.springframework.stereotype.Service;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.crear.CompraResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.update.CompraUpdateRequest;

@Service
public class UpdateCompraServiceImpl implements IUpdateCompraService {

    @Override
    public CompraResponse execute(CompraUpdateRequest request, Long id) {
        // TODO: Implementar lógica de negocio
        return new CompraResponse();
    }
}

