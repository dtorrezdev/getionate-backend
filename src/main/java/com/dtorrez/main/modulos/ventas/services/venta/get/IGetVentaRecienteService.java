package com.dtorrez.main.modulos.ventas.services.venta.get;

import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.get.GetVentaRecienteResponse;

import java.util.List;

public interface IGetVentaRecienteService {
    List<GetVentaRecienteResponse> execute();
}
