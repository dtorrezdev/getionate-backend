package com.dtorrez.main.modulos.producto.mappers;

import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.unidad_medida.*;
import com.micrium.bd.access.jpa.modulo.productos.models.UnidadMedida;

import java.util.function.Function;

public class UnidadMedidaMapper {

    public static final Function<UnidadMedidaRequest, UnidadMedida> fromRequestToEntity = req ->
            UnidadMedida.builder()
                    .nombre(req.getNombre())
                    .abreviatura(req.getAbreviatura())
                    .esUnidadMinima(Boolean.valueOf(req.getEsUnidadMinima()))
                    .esActivo(Boolean.TRUE)
                    .build();

    public static final Function<UnidadMedida, UnidadMedidaResponse> fromEntityToResponse =
            m -> new UnidadMedidaResponse(m.getId(), m.getAbreviatura(), m.getNombre(), m.getEsUnidadMinima());
}
