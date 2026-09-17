package com.dtorrez.main.modulos.compra.controllers.dtos.recepcion.crear;

import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.registrar.StockMovimientoDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetalleRecepcionRequest implements Serializable {
    @NotNull(message = "presentacionId no puede ser null")
    private Long presentacionId;

    @NotNull(message = "productoId no puede ser null")
    private Long productoId;

    @NotNull(message = "cantidad no puede ser null")
    @Min(value = 1, message = "cantidad debe ser mayor a 0")
    private Integer cantidad;

    @NotNull(message = "precio no puede ser null")
    @Min(value = 1, message = "precio debe ser mayor a 0")
    private BigDecimal precio;

    private List<StockMovimientoDto> stocks;
}

