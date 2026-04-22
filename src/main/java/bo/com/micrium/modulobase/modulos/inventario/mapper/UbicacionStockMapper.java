package bo.com.micrium.modulobase.modulos.inventario.mapper;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.ubicacion_stock.UbicacionStockRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.ubicacion_stock.UbicacionStockResponse;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.marca.MarcaRequest;
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
