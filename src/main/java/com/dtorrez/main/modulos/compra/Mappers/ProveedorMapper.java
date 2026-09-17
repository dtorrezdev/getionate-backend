package com.dtorrez.main.modulos.compra.Mappers;

import com.dtorrez.main.modulos.compra.controllers.dtos.proveedor.ProveedorRequest;
import com.dtorrez.main.modulos.compra.controllers.dtos.proveedor.ProveedorResponse;
import com.micrium.bd.access.jpa.modulo.compra.models.Proveedor;

import java.util.function.Function;

public class ProveedorMapper {

    public static final Function<ProveedorRequest, Proveedor> toEntity = req ->
            Proveedor.builder()
                    .nombre(req.getNombre())
                    .descripcion(req.getDescripcion())
                    .esActivo(Boolean.TRUE)
                    .build();

    public static final Function<Proveedor, ProveedorResponse> toResponse =
            m -> new ProveedorResponse(m.getId(), m.getNombre(), m.getDescripcion());
}
