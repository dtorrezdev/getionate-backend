package bo.com.micrium.modulobase.modulos.inventario.services.movimiento;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.venta.MovimientoVentaRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.venta.MovimientoVentaResponse;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.DetalleVentaRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.VentaRequest;

import java.util.List;

public interface ICreateMovimientoVentaService {


    MovimientoVentaResponse execute(List<DetalleVentaRequest> request);

}
