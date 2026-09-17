package com.dtorrez.main.modulos.ventas.controllers.dtos.venta.list;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
@ToString
public class ListVentaRequest implements Serializable {

    private String id;
    private String codigo;
    private String glosa;
    private String total;
    private String cliente;
    private String estado;
    private String fechaInicio;
    private String fechaFin;
}
