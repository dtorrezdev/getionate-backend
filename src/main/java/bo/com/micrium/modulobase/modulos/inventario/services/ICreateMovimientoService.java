package bo.com.micrium.modulobase.modulos.inventario.services;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.MovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.MovimientoResponse;

public interface ICreateMovimientoService {

    MovimientoResponse execute(MovimientoRequest createMovimientoRequest);
}
