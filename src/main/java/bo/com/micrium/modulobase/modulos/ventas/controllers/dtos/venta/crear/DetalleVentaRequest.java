package bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class DetalleVentaRequest implements Serializable {

    @NotNull(message = "Presentacion id no puede ser null")
    private Long presentacionId;
    @NotNull(message = "Producto id no puede ser null")
    private Long productoId;

    @NotNull(message = "Cantidad no puede ser null")
    @Min(value = 1, message = "Cantidad debe ser mayor a 0")
    private Integer cantidad;

    @NotNull(message = "Cantidad Base no puede ser null")
    @Min(value = 1, message = "Total debe ser mayor a 0")
    private Integer cantidadBase;

    @NotNull(message = "Precio Unitario no puede ser null")
    @Min(value = 1, message = "Total debe ser mayor a 0")
    private Double precioUnitario;

//    @NotNull(message = "Subtotal no puede ser null")
//    @Min(value = 1, message = "Subtotal debe ser mayor a 0")
//    private Double subtotal;
}
