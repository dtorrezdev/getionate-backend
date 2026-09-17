package com.dtorrez.main.modulos.inventario.mapper;

import com.dtorrez.main.modulos.inventario.controllers.dtos.ubicacion_stock.UbicacionStockRequest;
import com.dtorrez.main.modulos.inventario.controllers.dtos.ubicacion_stock.UbicacionStockResponse;
import com.micrium.bd.access.jpa.modulo.inventario.models.UbicacionStock;
import com.micrium.bd.access.jpa.modulo.productos.models.Marca;

import java.util.function.Function;

public class UbicacionStockMapper {

    public static final Function<UbicacionStockRequest, UbicacionStock> toEntity = req ->
            UbicacionStock.builder()
                    .seccion(req.getSeccion())
                    .estante(req.getEstante())
                    .nivel(req.getNivel())
                    .esActivo(Boolean.TRUE)
                    .build();

    public static final Function<UbicacionStock, UbicacionStockResponse>
            fromEntityToResponse = entity -> {
        UbicacionStockResponse response = new UbicacionStockResponse();
        response.setId(entity.getId());
        response.setSeccion(entity.getSeccion());
        response.setEstante(entity.getEstante());
        response.setNivel(entity.getNivel());
        return response;
    };
}
