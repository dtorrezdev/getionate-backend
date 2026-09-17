package com.dtorrez.main.modulos.ventas.services.venta.anular;

import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.AnularVentaRequest;

public interface IAnularVentaService {

    void execute(AnularVentaRequest request);
}
