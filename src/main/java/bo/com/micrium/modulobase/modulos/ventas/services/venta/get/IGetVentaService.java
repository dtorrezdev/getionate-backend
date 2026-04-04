package bo.com.micrium.modulobase.modulos.ventas.services.venta.get;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.get.GetVentaResponse;

public interface IGetVentaService {

    GetVentaResponse execute(Long ventaId);
}
