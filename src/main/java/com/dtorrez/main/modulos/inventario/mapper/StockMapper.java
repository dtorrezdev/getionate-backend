package com.dtorrez.main.modulos.inventario.mapper;

import com.dtorrez.main.modulos.inventario.controllers.dtos.stock.StockDisponibleDto;
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
        resp.setUbicacionStockId(entity.getUbicacionStockId());
        resp.setSeccion(entity.getSeccion());
        resp.setEstante(entity.getEstante());
        resp.setNivel(entity.getNivel());
        resp.setPresentacionId(entity.getPresentacionId());
        resp.setRegistroSanitario(entity.getRegistroSanitario());
        return resp;
    };
}
