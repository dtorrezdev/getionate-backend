package com.dtorrez.main.modulos.producto.controllers.dtos.categoria;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoriaRequest implements Serializable {
    private String id;
    private String nombre;
    private String descripcion;
}
