package bo.com.micrium.modulobase.modulos.compra.services.compra.get;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.get.GetOrdenCompraResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.solicitud.GetSolicitudCompraResponse;

public interface IGetCompraService {

    GetOrdenCompraResponse getOrden(Long compraId);

    GetSolicitudCompraResponse getSolicitud(Long compraId);
}

