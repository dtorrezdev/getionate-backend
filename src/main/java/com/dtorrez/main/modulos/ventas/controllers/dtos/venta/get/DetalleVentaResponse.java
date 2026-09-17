package com.dtorrez.main.modulos.ventas.controllers.dtos.venta.get;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class DetalleVentaResponse implements Serializable {
    private Long id;
    private Integer cantidad;
    private String producto;
    private BigDecimal precio;
    private BigDecimal subtotal;
}
