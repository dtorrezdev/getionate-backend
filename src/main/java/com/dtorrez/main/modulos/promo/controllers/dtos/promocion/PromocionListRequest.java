package com.dtorrez.main.modulos.promo.controllers.dtos.promocion;

import lombok.Data;

import java.io.Serializable;

@Data
public class PromocionListRequest implements Serializable {
    private String nombre;
    private String descripcion;
    private String fechaInicio;
    private String fechaFin;
    private String isActive;
    private String limiteUso;
    private String limitePorCliente;
}
