package bo.com.micrium.modulobase.modulos.producto.mappers;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.list.ListMovimientoProductoResponse;
import com.micrium.bd.access.jpa.modulo.inventario.projection.MovimientoProductoProjection;

import java.util.function.Function;

public class MovimientoProductoMapper {

    public static final Function<MovimientoProductoProjection, ListMovimientoProductoResponse>
    fromProjectionToResponse = entity -> {
        ListMovimientoProductoResponse response = new ListMovimientoProductoResponse();
        response.setProducto(entity.getProducto());
        response.setUnidadMedidaShort(entity.getUnidadMedidaShort());
        response.setFecha(entity.getFecha());
        response.setDesde(entity.getDesde());
        response.setCantidad(entity.getCantidad());
        response.setHasta(entity.getHasta());
        response.setLote(entity.getLote());
        response.setMotivo(entity.getMotivo());
        response.setTipoMovimiento(entity.getTipoMovimiento());
        response.setUnidadMedida(entity.getUnidadMedida());
        return response;
    };

}
