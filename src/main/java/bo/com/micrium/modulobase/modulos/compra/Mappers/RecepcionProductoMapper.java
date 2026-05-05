package bo.com.micrium.modulobase.modulos.compra.Mappers;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.crear.DetalleRecepcionRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.crear.RecepcionProductoRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.crear.RecepcionProductoResponse;
import com.micrium.bd.access.jpa.modulo.compra.models.DetalleRecepcion;
import com.micrium.bd.access.jpa.modulo.compra.models.RecepcionProducto;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RecepcionProductoMapper {

    public static final Function<DetalleRecepcionRequest, DetalleRecepcion> toDetalleEntity
            = dto ->
            DetalleRecepcion.builder()
                    .presentacionId(dto.getPresentacionId())
                    .productoId(dto.getProductoId())
                    .cantidad(dto.getCantidad())
                    .precio(dto.getPrecio())
                    .fechaVencimiento(dto.getFechaVencimiento())
                    .lote(dto.getLote())
                    .build();

    public static final Function<RecepcionProductoRequest, RecepcionProducto> toEntity = request -> {

        RecepcionProducto recepcion = new RecepcionProducto();
        recepcion.setFecha(new Timestamp(System.currentTimeMillis()));
        recepcion.setTotal(request.getTotal().doubleValue());
        recepcion.setGlosa(request.getGlosa());
        recepcion.setCompraId(request.getCompraId());
        recepcion.setMovimientoId(request.getMovimientoId());

        List<DetalleRecepcion> detalles = request.getDetalle().stream()
                .map(toDetalleEntity)
                .collect(Collectors.toList());

        recepcion.setDetalle(detalles);

        detalles.forEach(d -> d.setRecepcionProducto(recepcion));

        return recepcion;
    };

    public static final Function<RecepcionProducto, RecepcionProductoResponse> toResponse = recepcion -> {
        RecepcionProductoResponse response = new RecepcionProductoResponse();
        response.setId(recepcion.getId());
        return response;
    };

}


