package bo.com.micrium.modulobase.modulos.inventario.services.movimiento;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoResponse;

public interface ICreateMovimientoService {

    MovimientoResponse execute(MovimientoRequest createMovimientoRequest);
}
