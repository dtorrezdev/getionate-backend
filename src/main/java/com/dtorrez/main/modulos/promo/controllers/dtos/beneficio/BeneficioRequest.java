package com.dtorrez.main.modulos.promo.controllers.dtos.beneficio;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class BeneficioRequest implements Serializable {

    private Long promocionId;
    private String tipo;
    private BigDecimal valor;
    private BigDecimal maximoDescuento;
}
