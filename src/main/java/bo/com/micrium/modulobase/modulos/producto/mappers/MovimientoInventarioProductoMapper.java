package bo.com.micrium.modulobase.modulos.producto.mappers;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoProductoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.ItemMovimientoDto;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.RegistrarMovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.StockMovimientoDto;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;

public class MovimientoInventarioProductoMapper {

    public static final Function<MovimientoProductoRequest, RegistrarMovimientoRequest>
            fromProductoToRegistrarMovimiento = productoRequest -> {
        RegistrarMovimientoRequest request = new RegistrarMovimientoRequest();
        request.setTipoMovimientoId(productoRequest.getTipoMovimientoId()); // SALIDA
        request.setMotivo(productoRequest.getMotivo());
        request.setFecha(new Timestamp(System.currentTimeMillis()));

        ItemMovimientoDto item = new ItemMovimientoDto();
        // cantidad ya esta en del detalle stock
        //item.setCantidad(productoRequest.getCantidad());
        item.setProductoId(productoRequest.getProductoId());
        item.setPresentacionId(productoRequest.getPresentacionId());

        final List<StockMovimientoDto> stocks = productoRequest.getDetalleMovimiento().stream()
                .map(detalle -> {
                    StockMovimientoDto stock = new StockMovimientoDto();
                    stock.setLote(detalle.getLote());
                    stock.setExpiracion(detalle.getFechaExpiracion());
                    stock.setCantidad(detalle.getCantidadStockBase()); // eliminar cantidad Base
                    stock.setRegistroSanitario(detalle.getRegistroSanitario());
                    stock.setUbicacionStockId(productoRequest.getUbicacionStockId());
                    return stock;
                }).toList();
        item.setStocks(stocks);
        request.setItemMovimientos(List.of(item));

        return request;
    };
}
