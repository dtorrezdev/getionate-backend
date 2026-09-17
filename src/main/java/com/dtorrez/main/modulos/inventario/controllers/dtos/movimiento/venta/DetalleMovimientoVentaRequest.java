package com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.venta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class DetalleMovimientoVentaRequest implements Serializable {
    private Long stockId;
    private Integer cantidadStock;
    private Integer cantidadStockBase;
    private Long productoId;
    private Long presentacionId;
}
