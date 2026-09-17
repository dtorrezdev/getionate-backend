package com.dtorrez.main.modulos.ventas.mapper;

import com.dtorrez.main.modulos.ventas.controllers.dtos.pago.DetallePagoResponse;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.get.DetalleVentaResponse;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.get.GetDetalleResponse;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.get.GetVentaResponse;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.get.HeaderVentaResponse;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.list.ListVentaResponse;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.crear.DetalleVentaRequest;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.crear.VentaRequest;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.crear.VentaResponse;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.update.VentaUpdateRequest;
import com.micrium.bd.access.jpa.modulo.venta.models.DetalleVenta;
import com.micrium.bd.access.jpa.modulo.venta.models.Venta;
import com.micrium.bd.access.jpa.modulo.venta.projection.DetalleVentaProjection;
import com.micrium.bd.access.jpa.modulo.venta.projection.HeaderVentaProjection;
import com.micrium.bd.access.jpa.modulo.venta.projection.ListVentaProjection;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class VentaMapper {

    private VentaMapper() {
        throw new AssertionError();
    }

    public static final Function<DetalleVentaRequest, DetalleVenta> toDetalleEntity
            = dto ->
            DetalleVenta.builder()
                    .presentacionId(dto.getPresentacionId())
                    .productoId(dto.getProductoId())
                    .cantidad(dto.getCantidad())
                    .precio(dto.getPrecio())
//                    .subtotal(dto.getSubtotal())
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

    public static final Function<ListVentaProjection, ListVentaResponse>
            fromProjectionToListVentaResponse = venta -> {
        ListVentaResponse response = new ListVentaResponse();
        response.setId(venta.getId());
        response.setCliente(venta.getCliente());
        response.setEstado(venta.getEstado());
        response.setCodigo(venta.getCodigo());
        response.setGlosa(venta.getGlosa());
        response.setTotal(venta.getTotal());
        response.setClienteId(venta.getClienteId());
        response.setFechaRegistro(venta.getFechaRegistro());
        response.setMovimientoId(venta.getMovimientoId());
        response.setVendedor(venta.getVendedor());
        return response;
    };

    // Get Venta Response
    public static final Function<Venta, GetVentaResponse> entityToGetResponse = venta -> {
        GetVentaResponse response = new GetVentaResponse();
        response.setId(venta.getId());
        response.setClienteId(venta.getClienteId());
        response.setCodigo(venta.getCodigo());
        response.setFechaRegistro(venta.getFechaRegistro());
        response.setGlosa(venta.getGlosa());
        response.setEstado(venta.getEstado());
        response.setTotal(venta.getTotal());
        response.setMovimientoId(venta.getMovimientoId());
        final List<GetDetalleResponse> list = venta.getDetalle().stream()
                .map(VentaMapper.entitytoGetDetalleResponse)
                .toList();
        response.setDetalle(list);

        final List<DetallePagoResponse> detallePagos = venta.getPagos().stream().map(pago -> {
            DetallePagoResponse detalle = new DetallePagoResponse();
            detalle.setId(pago.getId());
            detalle.setTotal(pago.getTotal());
            detalle.setTipoPago(pago.getTipoPago());
            return detalle;
        }).toList();
        response.setPagos(detallePagos);

        return response;
    };

    public static final Function<DetalleVenta, GetDetalleResponse> entitytoGetDetalleResponse
            = detalle -> {
        GetDetalleResponse dto = new GetDetalleResponse();
        dto.setId(detalle.getId());
        dto.setCantidad(detalle.getCantidad());
        dto.setSubtotal(detalle.getSubtotal());
        dto.setPresentacionId(detalle.getPresentacionId());
        dto.setProductoId(detalle.getProductoId());
        dto.setPrecio(detalle.getPrecio());
            return dto;
    };

    // Update
    public static final Function<VentaUpdateRequest, Venta> fromUpdatetoEntity = request -> {

        Venta venta = new Venta();
        venta.setId(request.getId());
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

    // Nota de Venta
    public static final Function<HeaderVentaProjection, HeaderVentaResponse> toHeaderVenta = venta -> {
        HeaderVentaResponse response = new HeaderVentaResponse();
        response.setId(venta.getId());
        response.setCliente(venta.getCliente());
        response.setEstado(venta.getEstado());
        response.setCodigo(venta.getCodigo());
        response.setFechaRegistro(venta.getFechaRegistro());
        response.setClienteId(venta.getClienteId());
        response.setTotal(venta.getTotal());
        return response;
    };

    public static final Function<List<DetalleVentaProjection>, List<DetalleVentaResponse>>
            toDetalleVenta = venta ->
        venta.stream().map(
                detalle -> {
                    DetalleVentaResponse det = new DetalleVentaResponse();
                    det.setId(detalle.getId());
                    det.setCantidad(detalle.getCantidad());
                    det.setPrecio(detalle.getPrecio());
                    det.setProducto(detalle.getProducto());
                    det.setSubtotal(detalle.getSubtotal());
                    return det;
                }
        ).toList()
    ;
}
