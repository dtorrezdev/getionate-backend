package bo.com.micrium.modulobase.modulos.compra.Mappers;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.crear.DetalleRecepcionRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoProductoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.ItemMovimientoDto;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.RegistrarMovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.StockMovimientoDto;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.DetalleVentaRequest;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;

public class MovimientoInventarioRecepcionMapper {

    public static final Function<List<DetalleRecepcionRequest>, RegistrarMovimientoRequest>
            fromDetalleRecepcionToRegistrarMovimiento = detalleVentaRequests -> {
        RegistrarMovimientoRequest request = new RegistrarMovimientoRequest();
        request.setTipoMovimientoId(1L); // ENTRADA
        request.setMotivo("RECEPCION PRODUCTOS");
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
//                                newStock.setId(stock.getId());
                                newStock.setCantidad(stock.getCantidad());
                                newStock.setUbicacionStockId(stock.getUbicacionStockId());
                                newStock.setLote(stock.getLote());
                                newStock.setExpiracion(stock.getExpiracion());
                                return newStock;
                            })
                            .toList();
                    item.setStocks(stocks);
                    return item;
                }).toList();
        request.setItemMovimientos(itemMovimientos);

        return request;
    };
}
