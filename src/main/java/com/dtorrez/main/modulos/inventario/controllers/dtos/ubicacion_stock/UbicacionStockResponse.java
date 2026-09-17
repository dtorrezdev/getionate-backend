package com.dtorrez.main.modulos.inventario.controllers.dtos.ubicacion_stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
@ToString
public class UbicacionStockResponse implements Serializable {
    private Long id;
    private String seccion;
    private String estante;
    private String nivel;
}
