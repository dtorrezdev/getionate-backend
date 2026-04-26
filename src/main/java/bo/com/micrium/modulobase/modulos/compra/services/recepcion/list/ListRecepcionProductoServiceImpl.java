package bo.com.micrium.modulobase.modulos.compra.services.recepcion.list;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.list.ListRecepcionProductoRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.list.ListRecepcionProductoResponse;

import java.util.ArrayList;

@Service
public class ListRecepcionProductoServiceImpl implements IListRecepcionProductoService {

    @Override
    public Page<ListRecepcionProductoResponse> execute(ListRecepcionProductoRequest params, Pageable page) {
        // TODO: Implementar lógica de negocio
        return new PageImpl<>(new ArrayList<>(), page, 0);
    }
}

