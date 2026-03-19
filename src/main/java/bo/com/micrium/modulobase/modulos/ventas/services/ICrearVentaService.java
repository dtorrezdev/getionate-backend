package bo.com.micrium.modulobase.modulos.ventas.services;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.VentaRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.VentaResponse;

public interface ICrearVentaService {

    VentaResponse execute(VentaRequest crearVentaRequest);

}
