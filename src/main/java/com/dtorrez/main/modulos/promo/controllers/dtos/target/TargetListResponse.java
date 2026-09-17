package com.dtorrez.main.modulos.promo.controllers.dtos.target;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class TargetListResponse implements Serializable {
    private Long id;
    private Long promocionId;
    private String promocion;
    private Long productoId;
    private String producto;
    private Long categoriaId;
    private String categoria;
    private Long marcaId;
    private String marca;
}
