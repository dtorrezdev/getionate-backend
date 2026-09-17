package com.dtorrez.main.modulos.compra.controllers.dtos.orden_compra.list;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrdenCompraResponse implements Serializable {
    private Long id;
    private String codigo;
    private BigDecimal total;
    private String glosa;
    private Timestamp fecha;
    private Long provedorId;
    private String proveedor;
    private String estado;
    private String tipoCompra;
    private Integer nroItems;
    private String solicitante;
    private String aprobador;
}
