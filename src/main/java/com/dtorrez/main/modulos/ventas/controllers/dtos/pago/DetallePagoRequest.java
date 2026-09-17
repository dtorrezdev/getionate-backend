package com.dtorrez.main.modulos.ventas.controllers.dtos.pago;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DetallePagoRequest implements Serializable {
    @Size(max = 60, message = "Descripcion debe ser menor igual a 60 letras.")
    private String tipo;

    @Min(value = 1, message = "total pago debe ser mayor a 0")
    private BigDecimal monto;
}
