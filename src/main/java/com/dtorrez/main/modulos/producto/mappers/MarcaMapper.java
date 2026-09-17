package com.dtorrez.main.modulos.producto.mappers;

import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.marca.*;
import com.micrium.bd.access.jpa.modulo.productos.models.Marca;

import java.util.function.Function;

public class MarcaMapper {

    public static final Function<MarcaRequest, Marca> toEntity = req ->
        Marca.builder()
            .nombre(req.getNombre())
            .descripcion(req.getDescripcion())
                .esActivo(Boolean.TRUE)
            .build();

    public static final Function<Marca, MarcaResponse> toResponse =
            m -> new MarcaResponse(m.getId(), m.getNombre(), m.getDescripcion());
}
