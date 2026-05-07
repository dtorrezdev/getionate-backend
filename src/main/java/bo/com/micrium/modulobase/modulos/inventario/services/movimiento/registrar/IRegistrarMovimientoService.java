package bo.com.micrium.modulobase.modulos.inventario.services.movimiento.registrar;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.RegistrarMovimientoRequest;

public interface IRegistrarMovimientoService {
    void registrar(RegistrarMovimientoRequest request);
}
