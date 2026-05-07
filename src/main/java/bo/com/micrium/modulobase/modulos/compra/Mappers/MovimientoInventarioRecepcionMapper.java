package bo.com.micrium.modulobase.modulos.compra.Mappers;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoProductoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.ItemMovimientoDto;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.RegistrarMovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.StockMovimientoDto;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.DetalleVentaRequest;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;

public class MovimientoInventarioRecepcionMapper {

    public static final Function<List<DetalleVentaRequest>, RegistrarMovimientoRequest>
            fromDetalleVentaToRegistrarMovimiento = detalleVentaRequests -> {
        RegistrarMovimientoRequest request = new RegistrarMovimientoRequest();
        request.setTipoMovimientoId(2L); // SALIDA
        request.setMotivo("VENTA PRODUCTOS");
        request.setFecha(new Timestamp(System.currentTimeMillis()));

        final List<ItemMovimientoDto> itemMovimientos = detalleVentaRequests.stream()
                .map(detalle -> {
                    ItemMovimientoDto item = new ItemMovimientoDto();
                    item.setCantidad(detalle.getCantidad()); // cantidad a vender
                    item.setProductoId(detalle.getProductoId());
                    item.setPresentacionId(detalle.getPresentacionId());
                    final List<StockMovimientoDto> stocks = detalle.getStocks().stream()
                            .map(stock -> {
                                StockMovimientoDto newStock = new StockMovimientoDto();
                                newStock.setId(stock.getId());
                                newStock.setCantidad(stock.getCantidad());
                                return newStock;
                            })
                            .toList();
                    item.setStocks(stocks);
                    return item;
                }).toList();
        request.setItemMovimientos(itemMovimientos);

        return request;
    };

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
                    stock.setCantidad(detalle.getCantidadStock()); // eliminar cantidad Base
                    stock.setRegistroSanitario(detalle.getRegistroSanitario());
                    stock.setUbicacionStockId(productoRequest.getUbicacionStockId());
                    return stock;
                }).toList();
        item.setStocks(stocks);
        request.setItemMovimientos(List.of(item));

        return request;
    };

}
