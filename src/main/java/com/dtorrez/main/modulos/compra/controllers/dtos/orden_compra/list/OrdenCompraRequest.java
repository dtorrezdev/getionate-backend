package com.dtorrez.main.modulos.compra.controllers.dtos.orden_compra.list;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
@ToString
public class OrdenCompraRequest implements Serializable {

    private String id;
    private String total;
    private String codigo;
    private String glosa;
    private String provedor;
    private String estado;
    private String fecha;
    private String tipoCompra;
    private String solicitante;
    private String aprobador;
}

