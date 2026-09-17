package com.dtorrez.main.modulos.ventas.services.venta.get;

import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.get.GetNotaVentaResponse;

public interface IGetNotaVentaService {
    GetNotaVentaResponse execute(Long ventaId);
}
