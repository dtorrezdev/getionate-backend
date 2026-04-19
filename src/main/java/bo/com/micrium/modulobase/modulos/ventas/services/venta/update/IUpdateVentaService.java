package bo.com.micrium.modulobase.modulos.ventas.services.venta.update;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.VentaResponse;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.update.VentaUpdateRequest;

public interface IUpdateVentaService {
    VentaResponse execute(VentaUpdateRequest crearVentaRequest, Long id);
}
