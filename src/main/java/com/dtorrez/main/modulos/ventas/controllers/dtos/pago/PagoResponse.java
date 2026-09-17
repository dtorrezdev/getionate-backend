package com.dtorrez.main.modulos.ventas.controllers.dtos.pago;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PagoResponse implements Serializable {

    private List<DetallePagoResponse> pagos;
}
