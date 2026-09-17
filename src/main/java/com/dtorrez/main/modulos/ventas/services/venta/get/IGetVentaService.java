package com.dtorrez.main.modulos.ventas.services.venta.get;

import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.get.GetVentaResponse;
import com.micrium.bd.access.jpa.modulo.venta.models.Venta;

public interface IGetVentaService {

    GetVentaResponse execute(Long ventaId);
}
