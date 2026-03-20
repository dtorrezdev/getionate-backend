package bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class VentaRequest implements Serializable {
    @NotNull(message = "total no puede ser null")
    @Min(value = 1, message = "total debe ser mayor a 0")
    private Double total;

    @NotNull(message = "codigo no puede ser null")
    private String codigo;

    private String glosa;

    @NotNull(message = "cliente Id no puede ser null")
    private Long clienteId;

    private String estado;  // PREVENTA, FINALIZADA

    private Long movimientoId;

    @Valid
    private ArrayList<DetalleVentaRequest> detalle;
}
