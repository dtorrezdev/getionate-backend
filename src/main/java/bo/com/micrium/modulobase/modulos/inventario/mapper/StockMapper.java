package bo.com.micrium.modulobase.modulos.inventario.mapper;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleDto;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleResponse;
import com.micrium.bd.access.jpa.modulo.inventario.projection.StockDisponibleProjection;

import java.util.function.Function;

public class StockMapper {

    public static final Function<StockDisponibleProjection, StockDisponibleDto>
        toResponse = entity -> {
        StockDisponibleDto resp = new StockDisponibleDto();
        resp.setId(entity.getId());
        resp.setExpiracion(entity.getExpiracion());
        resp.setLote(entity.getLote());
        resp.setCantidad(entity.getCantidad());
        resp.setSeccion(entity.getSeccion());
        resp.setEstante(entity.getEstante());
        resp.setNivel(entity.getNivel());
        resp.setPresentacionId(entity.getPresentacionId());
        return resp;
    };
}
