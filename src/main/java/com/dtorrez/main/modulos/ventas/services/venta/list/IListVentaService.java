package com.dtorrez.main.modulos.ventas.services.venta.list;

import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.list.ListVentaRequest;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.list.ListVentaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IListVentaService {

    Page<ListVentaResponse> execute(ListVentaRequest params, Pageable page);

}
