package com.dtorrez.main.modulos.producto.controllers.dtos.producto_presentacion.list;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
@ToString
public class ListPresentacionRequest implements Serializable {
    private String productoId;
    private String producto;
    private String codigo;
    private String presentacion;
    private String descripcion;
    private String principioActivo;
    private String unidadMedida;
    private String marca;
    private String categoria;
    private String seControlaStock;
}
