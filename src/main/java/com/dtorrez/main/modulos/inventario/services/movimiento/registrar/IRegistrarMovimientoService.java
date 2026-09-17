package com.dtorrez.main.modulos.inventario.services.movimiento.registrar;

import com.dtorrez.main.modulos.compra.controllers.dtos.recepcion.crear.DetalleRecepcionRequest;
import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoProductoRequest;
import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoResponse;
import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.registrar.RegistrarMovimientoRequest;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.crear.DetalleVentaRequest;

import java.util.List;

public interface IRegistrarMovimientoService {
    MovimientoResponse registrar(RegistrarMovimientoRequest request);

    MovimientoResponse registrar(MovimientoProductoRequest request);

    Long registrar(List<DetalleVentaRequest> detalleVenta);

    Long registrarRecepcion(List<DetalleRecepcionRequest> detalleRecepcion);

}
