package bo.com.micrium.modulobase.modulos.ventas.services.venta.create;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.VentaRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.VentaResponse;

public interface ICreateVentaService {

    VentaResponse execute(VentaRequest crearVentaRequest);

}

