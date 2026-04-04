package bo.com.micrium.modulobase.modulos.inventario.mapper;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.DetalleMovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.MovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.MovimientoResponse;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.DetalleVentaRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.VentaRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.VentaResponse;
import com.micrium.bd.access.jpa.modulo.inventario.models.Movimiento;
import com.micrium.bd.access.jpa.modulo.inventario.models.MovimientoProducto;
import com.micrium.bd.access.jpa.modulo.inventario.models.Stock;
import com.micrium.bd.access.jpa.modulo.venta.models.DetalleVenta;
import com.micrium.bd.access.jpa.modulo.venta.models.Venta;
//import com.micrium.bd.access.jpa.modulo.venta.models.DetalleVenta;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MovimientoMapper {

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
            fromMovimientoEntityToMovimientoResponse = venta -> {
        MovimientoResponse response = new MovimientoResponse();
        response.setId(venta.getId());
        return response;
    };
}
