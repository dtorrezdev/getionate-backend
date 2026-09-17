package com.dtorrez.main.modulos.ventas.controllers.dtos.venta.crear;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
@ToString
public class VentaResponse implements Serializable {

    private Long id;
}
