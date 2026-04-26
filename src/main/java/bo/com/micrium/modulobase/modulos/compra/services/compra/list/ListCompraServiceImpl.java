package bo.com.micrium.modulobase.modulos.compra.services.compra.list;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.list.ListCompraRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.list.ListCompraResponse;

import java.util.ArrayList;

@Service
public class ListCompraServiceImpl implements IListCompraService {

    @Override
    public Page<ListCompraResponse> execute(ListCompraRequest params, Pageable page) {
        // TODO: Implementar lógica de negocio
        return new PageImpl<>(new ArrayList<>(), page, 0);
    }
}

