package bo.com.micrium.modulobase.modulos.ventas.services.pago;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.pago.PagoRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.pago.PagoResponse;
import com.micrium.bd.access.jpa.modulo.venta.models.Pago;

import java.util.List;

public interface IPagoService {

    PagoResponse save(PagoRequest request);

}
