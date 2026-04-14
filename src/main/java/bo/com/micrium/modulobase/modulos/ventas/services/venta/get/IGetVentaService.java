package bo.com.micrium.modulobase.modulos.ventas.services.venta.get;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.get.GetVentaResponse;
import com.micrium.bd.access.jpa.modulo.venta.models.Venta;

public interface IGetVentaService {

    GetVentaResponse execute(Long ventaId);
}
