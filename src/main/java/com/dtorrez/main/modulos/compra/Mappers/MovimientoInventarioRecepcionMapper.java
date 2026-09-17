package com.dtorrez.main.modulos.compra.Mappers;

import com.dtorrez.main.modulos.compra.controllers.dtos.recepcion.crear.DetalleRecepcionRequest;
import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.registrar.ItemMovimientoDto;
import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.registrar.RegistrarMovimientoRequest;
import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.registrar.StockMovimientoDto;

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
                    item.setCantidad(detalle.getCantidad()); // cantidad a comprar
                    item.setProductoId(detalle.getProductoId());
                    item.setPresentacionId(detalle.getPresentacionId());
                    final List<StockMovimientoDto> stocks = detalle.getStocks().stream()
                            .map(stock -> {
                                StockMovimientoDto newStock = new StockMovimientoDto();
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
