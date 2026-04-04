package bo.com.micrium.modulobase.modulos.inventario.services;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.*;

public interface ICreateMovimientoService {

    MovimientoResponse execute(MovimientoRequest createMovimientoRequest);
}
