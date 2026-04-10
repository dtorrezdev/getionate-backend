package bo.com.micrium.modulobase.modulos.inventario.mapper;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.DetalleMovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoResponse;
import com.micrium.bd.access.jpa.modulo.inventario.models.Movimiento;
import com.micrium.bd.access.jpa.modulo.inventario.models.MovimientoProducto;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MovimientoMapper {

    private MovimientoMapper() {
        throw new AssertionError();
    }

    public static final Function<DetalleMovimientoRequest, MovimientoProducto>
            fromDetalleReqyestToDetalleEntity = dto ->
            MovimientoProducto.builder()
                    .cantidad(dto.getCantidadStock())
                    .cantidadBase(dto.getCantidadStockBase())
                    //.stock(null)
                    .build();

    public static final Function<MovimientoRequest, Movimiento>
            fromMovimientoRequestToMovimientoEntity = request -> {

        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(new Timestamp(System.currentTimeMillis()));
        movimiento.setTipoMovimientoId(request.getTipoMovimientoId());
        movimiento.setMotivo(request.getMotivo());

        List<MovimientoProducto> detalles = request.getDetalleMovimiento().stream()
                .map(fromDetalleReqyestToDetalleEntity)
                .collect(Collectors.toList());

        movimiento.setDetalleMovimiento(detalles);

        detalles.forEach(d -> d.setMovimiento(movimiento));

        return movimiento;
    };

    public static final Function<Movimiento, MovimientoResponse>
            fromMovimientoEntityToMovimientoResponse = entity -> {
        MovimientoResponse response = new MovimientoResponse();
        response.setId(entity.getId());
        return response;
    };
}
