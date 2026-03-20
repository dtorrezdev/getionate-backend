package bo.com.micrium.modulobase.modulos.ventas.mapper;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.DetalleVentaRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.VentaRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.VentaResponse;
import com.micrium.bd.access.jpa.modulo.venta.models.DetalleVenta;
import com.micrium.bd.access.jpa.modulo.venta.models.Venta;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class VentaMapper {

    public static final Function<DetalleVentaRequest, DetalleVenta> toDetalleEntity
            = dto ->
            DetalleVenta.builder()
                    .presentacionId(dto.getPresentacionId())
                    .productoId(dto.getProductoId())
                    .cantidad(dto.getCantidad())
                    .cantidadBase(dto.getCantidadBase())
                    .precioUnitario(dto.getPrecioUnitario())
                    .subtotal(dto.getSubtotal())
                    .build();

    public static final Function<VentaRequest, Venta> toEntity = request -> {

        Venta venta = new Venta();
//        venta.setId(null);
        venta.setFechaRegistro(new Timestamp(System.currentTimeMillis()));
        venta.setEstado(request.getEstado());
        venta.setTotal(request.getTotal());
        venta.setGlosa(request.getGlosa());
        venta.setCodigo(request.getCodigo());
        venta.setClienteId(request.getClienteId());
        venta.setMovimientoId(null);

        List<DetalleVenta> detalles = request.getDetalle().stream()
                .map(toDetalleEntity)
                .collect(Collectors.toList());

        venta.setDetalle(detalles);

        detalles.forEach(d -> d.setVenta(venta));

        return venta;
    };

    public static final Function<Venta, VentaResponse> toResponse = venta -> {
        VentaResponse response = new VentaResponse();
        response.setId(venta.getId());
        return response;
    };

//    public static final Function<Venta, VentaResponse> toResponse = venta -> {
//
//        VentaResponse response = new VentaResponse();
//        response.setId(venta.getId());
//        response.setCliente(venta.getCliente());
//        response.setTotal(venta.getTotal());
//
//        List<DetalleVentaResponse> detalles = venta.getDetalles().stream()
//                .map(toDetalleResponse)
//                .collect(Collectors.toList());
//
//        response.setDetalles(detalles);
//
//        return response;
//    };


//    public static final Function<DetalleVenta, DetalleVentaResponse> toDetalleResponse = detalle -> {
//
//        DetalleVentaResponse d = new DetalleVentaResponse();
//        d.setProductoId(detalle.getProductoId());
//        d.setCantidad(detalle.getCantidad());
//        d.setPrecio(detalle.getPrecio());
//        d.setSubtotal(detalle.getSubtotal());
//        return d;
//    };
}
