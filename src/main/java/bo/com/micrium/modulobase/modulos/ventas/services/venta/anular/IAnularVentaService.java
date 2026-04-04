package bo.com.micrium.modulobase.modulos.ventas.services.venta.anular;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.AnularVentaRequest;

public interface IAnularVentaService {

    void execute(AnularVentaRequest request);
}
