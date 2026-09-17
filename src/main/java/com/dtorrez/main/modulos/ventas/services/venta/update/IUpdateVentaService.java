package com.dtorrez.main.modulos.ventas.services.venta.update;

import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.crear.VentaResponse;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.update.VentaUpdateRequest;

public interface IUpdateVentaService {
    VentaResponse execute(VentaUpdateRequest crearVentaRequest, Long id);
}
