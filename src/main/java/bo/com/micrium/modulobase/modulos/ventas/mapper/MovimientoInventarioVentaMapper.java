package bo.com.micrium.modulobase.modulos.ventas.mapper;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.ItemMovimientoDto;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.RegistrarMovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.StockMovimientoDto;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.DetalleVentaRequest;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;

public class MovimientoInventarioVentaMapper {

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



}
