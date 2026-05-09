package bo.com.micrium.modulobase.modulos.inventario.services.movimiento.registrar;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.crear.DetalleRecepcionRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoProductoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoResponse;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.RegistrarMovimientoRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.DetalleVentaRequest;

import java.util.List;

public interface IRegistrarMovimientoService {
    MovimientoResponse registrar(RegistrarMovimientoRequest request);

    MovimientoResponse registrar(MovimientoProductoRequest request);

    Long registrar(List<DetalleVentaRequest> detalleVenta);

    Long registrarRecepcion(List<DetalleRecepcionRequest> detalleRecepcion);

}
