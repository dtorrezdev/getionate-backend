package com.dtorrez.main.modulos.administracion.controllers.dtos.reporte;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class EstadisticaResponse implements Serializable {
    private Long cantidadProducto;
    private BigDecimal totalVentas;
    private Long cantidadCompras;
    private Long cantidadCliente;
}
