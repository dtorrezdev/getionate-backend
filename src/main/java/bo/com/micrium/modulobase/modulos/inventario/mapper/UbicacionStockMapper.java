package bo.com.micrium.modulobase.modulos.inventario.mapper;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.ubicacion_stock.UbicacionStockResponse;
import com.micrium.bd.access.jpa.modulo.inventario.models.UbicacionStock;

import java.util.function.Function;

public class UbicacionStockMapper {

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
