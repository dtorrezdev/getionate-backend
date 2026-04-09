package bo.com.micrium.modulobase.modulos.inventario.mapper;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleByProductoResponse;
import com.micrium.bd.access.jpa.modulo.inventario.projection.StockDisponibleProjection;

import java.util.function.Function;

public class StockMapper {

    public static final Function<StockDisponibleProjection, StockDisponibleByProductoResponse>
        toResponse = entity -> {
        StockDisponibleByProductoResponse resp = new StockDisponibleByProductoResponse();
        resp.setId(entity.getId());
        resp.setExpiracion(entity.getExpiracion());
        resp.setLote(entity.getLote());
        resp.setCantidad(entity.getCantidad());
        resp.setMovimientoProductoId(entity.getMovimientoProductoId());
        resp.setSeccion(entity.getSeccion());
        resp.setEstante(entity.getEstante());
        resp.setNivel(entity.getNivel());

        return resp;
    };
}
