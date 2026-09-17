package com.dtorrez.main.modulos.compra.controllers.dtos.solicitud;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class SolicitudCompraRequest implements Serializable {
    private String codigo;
    private BigDecimal total;
    private String glosa;
    private String fecha;
    private String estado;
    private Integer nroItems;
    private String solicitante;
    private String aprobador;
}
