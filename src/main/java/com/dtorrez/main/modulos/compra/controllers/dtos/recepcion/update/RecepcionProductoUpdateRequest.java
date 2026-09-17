package com.dtorrez.main.modulos.compra.controllers.dtos.recepcion.update;

import com.dtorrez.main.modulos.compra.controllers.dtos.recepcion.crear.DetalleRecepcionRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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
@JsonIgnoreProperties(ignoreUnknown = false)
public class RecepcionProductoUpdateRequest implements Serializable {
    @NotNull(message = "RecepcionProducto Id no puede ser null.")
    private Long id;

    @NotNull(message = "total no puede ser null.")
    @Min(value = 1, message = "total debe ser mayor a 0.")
    private BigDecimal total;

    @NotNull(message = "proveedor Id no puede ser null.")
    private Long proveedorId;

    private String glosa;

    private Long movimientoId;

    @NotEmpty(message = "Debe existir al menos una fila en detalle.")
    @Valid
    private List<DetalleRecepcionRequest> detalle;
}

