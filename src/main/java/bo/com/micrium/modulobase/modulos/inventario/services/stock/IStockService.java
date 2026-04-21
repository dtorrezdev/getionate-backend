package bo.com.micrium.modulobase.modulos.inventario.services.stock;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.DetalleMovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleDto;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleResponse;
import com.micrium.bd.access.jpa.modulo.inventario.models.Stock;

import java.util.List;
import java.util.Map;

public interface IStockService {

    // RF: deberia recibir StockRequest, pero por ahora se recibe el detalle del movimiento para actualizar el stock
    Stock save(MovimientoRequest request, DetalleMovimientoRequest detalle);

    // RF: deberia recibir StockRequest, demas esta logica deberia ser privada del servicio,
    // pero por ahora se esta exponiendo para ser usada en MovimientoService
    Stock resolverStock(MovimientoRequest request, DetalleMovimientoRequest detalle);

    // Obtener los stock de un producto especifico
    List<StockDisponibleDto> stockDisponibleByPresentacionId(Long presentacionId);

    // Obtiene todo los stock disponibles, agrupado por productoId
    Map<Long, List<StockDisponibleDto>> stockDisponibles();

    boolean validateUbicacionStock(Long ubicacionStockId);

    boolean hayStockDisponibleByProductoId(Long productoId, Long presentacionId, Integer cantidadAVender);

    boolean hayStockDisponibleByPresentacionId(Long presentacionId, Integer cantidadAVender);

}
