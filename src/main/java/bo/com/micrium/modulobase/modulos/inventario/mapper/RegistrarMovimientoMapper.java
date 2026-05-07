package bo.com.micrium.modulobase.modulos.inventario.mapper;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.DetalleMovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoProductoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.ItemMovimientoDto;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.RegistrarMovimientoRequest;
import com.micrium.bd.access.jpa.modulo.inventario.models.Movimiento;
import com.micrium.bd.access.jpa.modulo.inventario.models.MovimientoProducto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RegistrarMovimientoMapper {


    public static final Function<ItemMovimientoDto, MovimientoProducto>
            fromDetalleReqyestToDetalleEntity = dto ->
            MovimientoProducto.builder()
                    .cantidad(dto.getCantidad())
                    .cantidadBase(dto.getCantidad())
                    //.stock(null)
                    .build();

    public static final Function<RegistrarMovimientoRequest, Movimiento>
            fromMovimientoRequestToMovimientoEntity = request -> {

        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(request.getFecha());
        movimiento.setTipoMovimientoId(request.getTipoMovimientoId());
        movimiento.setMotivo(request.getMotivo());

        List<MovimientoProducto> detalles = new ArrayList<>();
//                request.getItemMovimientos().stream()
//                .map(fromDetalleReqyestToDetalleEntity)
//                .collect(Collectors.toList());

        movimiento.setDetalleMovimiento(detalles);

        detalles.forEach(d -> d.setMovimiento(movimiento));

        return movimiento;
    };


}
