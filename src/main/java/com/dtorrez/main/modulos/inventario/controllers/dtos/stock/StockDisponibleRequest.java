package com.dtorrez.main.modulos.inventario.controllers.dtos.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
@ToString
public class StockDisponibleRequest implements Serializable {
    private Long productoId;
    private Long presentacionId;

}
