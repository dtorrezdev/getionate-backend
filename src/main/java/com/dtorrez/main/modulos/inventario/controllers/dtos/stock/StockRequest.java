package com.dtorrez.main.modulos.inventario.controllers.dtos.stock;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class StockRequest implements Serializable {
    private Long id;
    private Integer cantidad;
}
