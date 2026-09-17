package com.dtorrez.main.modulos.inventario.mapper;

import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.venta.DetalleMovimientoVentaRequest;
import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.venta.MovimientoVentaRequest;
import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.venta.MovimientoVentaResponse;
import com.dtorrez.main.modulos.inventario.controllers.dtos.stock.StockRequest;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.crear.DetalleVentaRequest;
import com.micrium.bd.access.jpa.modulo.inventario.models.Movimiento;
import com.micrium.bd.access.jpa.modulo.inventario.models.MovimientoProducto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MovimientoVentaMapper {

    private static final Logger log = LogManager.getLogger(MovimientoVentaMapper.class);

    public static MovimientoVentaRequest fromVentaRequestToMovimiento(List<DetalleVentaRequest> request) {
        log.info("MovimientoVentaMapper llego" );
        MovimientoVentaRequest movimientoVenta = new MovimientoVentaRequest();
        movimientoVenta .setTipoMovimientoId(2L); // SALIDA
        movimientoVenta.setMotivo("VENTA PRODUCTO");
        List<DetalleMovimientoVentaRequest> newMovimientos = new ArrayList<>();
        for(DetalleVentaRequest detalle : request) {

            Integer cantidadBase = detalle.getCantidad();

            for (StockRequest stock : detalle.getStocks()) {
                DetalleMovimientoVentaRequest detMovi = new DetalleMovimientoVentaRequest();
                final int diff = stock.getCantidad() - cantidadBase;
                detMovi.setStockId(stock.getId());
                detMovi.setProductoId(detalle.getProductoId());
                detMovi.setPresentacionId(detalle.getPresentacionId());
                detMovi.setCantidadStockBase((stock.getCantidad()>=cantidadBase)? cantidadBase: stock.getCantidad());
                detMovi.setCantidadStock(detMovi.getCantidadStockBase());
                newMovimientos.add(detMovi);
                if(diff >= 0) {
                    break;
                }
                cantidadBase = Math.abs(diff);
            }
        }
        movimientoVenta.setDetalleMovimiento(newMovimientos);
        log.info("movimientoVenta: " + movimientoVenta.toString());
        return movimientoVenta;
    }

    public static final Function<DetalleMovimientoVentaRequest, MovimientoProducto>
            fromDetalleVentaReqyestToDetalleEntity = dto ->
            MovimientoProducto.builder()
                    .cantidad(dto.getCantidadStock())
                    .cantidadBase(dto.getCantidadStockBase())
                    //.stock(null)
                    .build();

    public static final Function<MovimientoVentaRequest, Movimiento>
            fromMovimientoRequestToMovimientoEntity = request -> {

        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(new Timestamp(System.currentTimeMillis()));
        movimiento.setTipoMovimientoId(request.getTipoMovimientoId());
        movimiento.setMotivo(request.getMotivo());

        List<MovimientoProducto> detalles = request.getDetalleMovimiento().stream()
                .map(fromDetalleVentaReqyestToDetalleEntity)
                .collect(Collectors.toList());

        movimiento.setDetalleMovimiento(detalles);

        detalles.forEach(d -> d.setMovimiento(movimiento));

        return movimiento;
    };

    public static final Function<Movimiento, MovimientoVentaResponse>
            fromEntityToResponse = entity -> {
        MovimientoVentaResponse response = new MovimientoVentaResponse();
        response.setId(entity.getId());
        return response;
    };
}
