package com.dtorrez.main.modulos.promo.controllers.dtos.uso;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class UsoListRequest implements Serializable {
    private String promocionId;
    private String clienteId;
    private String ventaId;
    private String cantidadDescuento;
}
