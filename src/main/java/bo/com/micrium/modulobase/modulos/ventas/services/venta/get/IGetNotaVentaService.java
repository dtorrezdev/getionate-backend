package bo.com.micrium.modulobase.modulos.ventas.services.venta.get;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.get.GetNotaVentaResponse;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.get.GetVentaResponse;

public interface IGetNotaVentaService {
    GetNotaVentaResponse execute(Long ventaId);
}
