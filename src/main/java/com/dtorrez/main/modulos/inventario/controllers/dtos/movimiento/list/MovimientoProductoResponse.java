package com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.list;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class MovimientoProductoResponse {
    private Timestamp fecha;
    private String tipoMovimiento;
    private String motivo;
    private String producto;
    private BigDecimal precioVenta;
    private String desde;
    private String hasta;
    private String lote;
    private Integer cantidad;
    private String unidadMedida;
    private String unidadMedidaShort;
}
