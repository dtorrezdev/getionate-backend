package bo.com.micrium.modulobase.modulos.producto.mappers;

import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto.*;
import com.micrium.bd.access.jpa.modulo.productos.models.Producto;
import com.micrium.bd.access.jpa.modulo.productos.projection.ProductoProjection;

import java.util.function.Function;

public class ProductoMapper {

    public static final Function<ProductoRequest, Producto> toEntity = req ->
        Producto.builder()
            .codigo(req.getCodigo())
            .nombre(req.getNombre())
            .descripcion(req.getDescripcion())
            .categoriaId(req.getCategoriaId())
            .esActivo(Boolean.TRUE)
            .build();

    public static final Function<Producto, ProductoResponse> toResponse =
            p -> new ProductoResponse(p.getId(), p.getCodigo(), p.getNombre(), p.getDescripcion(), p.getCategoriaId(), null);

    public static final Function<ProductoProjection, ProductoResponse> fromProjectiontoResponse =
            p -> new ProductoResponse(p.getId(), p.getCodigo(), p.getNombre(), p.getDescripcion(), p.getCategoriaId(), p.getCategoria());
}

