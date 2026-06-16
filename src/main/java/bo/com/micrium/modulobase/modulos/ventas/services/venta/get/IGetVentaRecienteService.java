package bo.com.micrium.modulobase.modulos.ventas.services.venta.get;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.get.GetVentaRecienteResponse;

import java.util.List;

public interface IGetVentaRecienteService {
    List<GetVentaRecienteResponse> execute();
}
