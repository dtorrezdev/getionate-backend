package com.dtorrez.main.modulos.ventas.services.pago;

import com.dtorrez.main.modulos.ventas.controllers.dtos.pago.PagoRequest;
import com.dtorrez.main.modulos.ventas.controllers.dtos.pago.PagoResponse;
import com.micrium.bd.access.jpa.modulo.venta.models.Pago;

public interface IPagoService {

    PagoResponse save(PagoRequest request);

}
