package com.dtorrez.main.modulos.compra.controllers.dtos.solicitud;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class SolicitudCompraResponse implements Serializable {
    private Long id;
    private String codigo;
    private BigDecimal total;
    private String glosa;
    private Timestamp fecha;
    private String estado;
    private Integer nroItems;
    private String solicitante;
    private String aprobador;
}
