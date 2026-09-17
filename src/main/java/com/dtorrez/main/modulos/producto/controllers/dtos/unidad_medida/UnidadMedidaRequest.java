package com.dtorrez.main.modulos.producto.controllers.dtos.unidad_medida;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class UnidadMedidaRequest implements Serializable {
    private Long id;
    private String abreviatura;
    private String nombre;
    private String esUnidadMinima;
}
