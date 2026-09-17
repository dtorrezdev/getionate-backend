package com.dtorrez.main.modulos.producto.controllers.dtos.categoria;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class CategoriaResponse implements Serializable {
    private Long id;
    private String nombre;
    private String descripcion;
}
