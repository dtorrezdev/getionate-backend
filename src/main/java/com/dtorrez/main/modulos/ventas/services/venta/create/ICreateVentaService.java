package com.dtorrez.main.modulos.ventas.services.venta.create;

import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.crear.VentaRequest;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.crear.VentaResponse;

public interface ICreateVentaService {

    VentaResponse execute(VentaRequest crearVentaRequest);

}

