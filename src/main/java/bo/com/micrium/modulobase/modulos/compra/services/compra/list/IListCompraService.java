package bo.com.micrium.modulobase.modulos.compra.services.compra.list;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.list.OrdenCompraRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.list.OrdenCompraResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.solicitud.SolicitudCompraRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.solicitud.SolicitudCompraResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IListCompraService {

    Page<OrdenCompraResponse> listOrdenCompra(OrdenCompraRequest params, Pageable page);


    Page<SolicitudCompraResponse> listSolicitudCompra(SolicitudCompraRequest params, Pageable page);

}

