package bo.com.micrium.modulobase.modulos.compra.services.compra.list;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.list.ListCompraRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.list.ListCompraResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IListCompraService {

    Page<ListCompraResponse> execute(ListCompraRequest params, Pageable page);

}

