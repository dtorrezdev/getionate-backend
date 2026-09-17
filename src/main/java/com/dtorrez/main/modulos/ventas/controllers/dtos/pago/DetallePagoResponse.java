package com.dtorrez.main.modulos.ventas.controllers.dtos.pago;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DetallePagoResponse {
    private Long id;
    private String tipoPago;
    private BigDecimal total;
    private Long ventaId;

}
