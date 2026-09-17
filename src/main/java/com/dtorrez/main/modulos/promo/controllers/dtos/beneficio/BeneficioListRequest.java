package com.dtorrez.main.modulos.promo.controllers.dtos.beneficio;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class BeneficioListRequest implements Serializable {

    private String promocionId;
    private String tipo;
    private String valor;
    private String maximoDescuento;
}
