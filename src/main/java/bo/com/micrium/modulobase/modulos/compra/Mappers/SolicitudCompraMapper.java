package bo.com.micrium.modulobase.modulos.compra.Mappers;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.list.OrdenCompraResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.solicitud.SolicitudCompraResponse;
import com.micrium.bd.access.jpa.modulo.compra.projection.ListCompraProjection;

import java.util.function.Function;

public class SolicitudCompraMapper {

    public static final Function<ListCompraProjection, SolicitudCompraResponse>
            fromProjectionToSolicitudCompraResponse = compra -> {
        SolicitudCompraResponse response = new SolicitudCompraResponse();
        response.setId(compra.getId());
        response.setEstado(compra.getEstado());
        response.setCodigo(compra.getCodigoSolicitud());
        response.setGlosa(compra.getGlosa());
        response.setTotal(compra.getTotal());
        response.setSolicitante(compra.getSolicitante());
        response.setAprobador(compra.getAprobador());
        response.setFecha(compra.getFechaSolicitud());
        response.setNroItems(compra.getNroItems());
        return response;
    };
}
