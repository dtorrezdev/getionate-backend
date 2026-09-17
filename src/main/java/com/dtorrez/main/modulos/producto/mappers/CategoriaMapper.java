package com.dtorrez.main.modulos.producto.mappers;

import com.dtorrez.main.modulos.producto.controllers.dtos.categoria.CategoriaRequest;
import com.dtorrez.main.modulos.producto.controllers.dtos.categoria.CategoriaResponse;
import com.micrium.bd.access.jpa.modulo.productos.models.Categoria;
import com.micrium.bd.access.jpa.modulo.productos.models.Marca;

import java.util.function.Function;

public class CategoriaMapper {

    public static final Function<CategoriaRequest, Categoria> toEntity = req ->
            Categoria.builder()
                    .nombre(req.getNombre())
                    .descripcion(req.getDescripcion())
                    .esActivo(Boolean.TRUE)
                    .build();

    public static final Function<Categoria, CategoriaResponse> toResponse =
            m -> new CategoriaResponse(m.getId(), m.getNombre(), m.getDescripcion());

}
