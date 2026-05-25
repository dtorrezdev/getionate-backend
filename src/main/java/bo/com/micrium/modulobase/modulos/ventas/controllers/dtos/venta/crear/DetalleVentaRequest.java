package bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = false)
public class DetalleVentaRequest implements Serializable {

    @NotNull(message = "Presentacion id no puede ser null")
    private Long presentacionId;
    @NotNull(message = "Producto id no puede ser null")
    private Long productoId;

    @NotNull(message = "Cantidad no puede ser null")
    @Min(value = 1, message = "Cantidad debe ser mayor a 0")
    private Integer cantidad;

    @NotNull(message = "Precio no puede ser null")
    @Min(value = 1, message = "Precio debe ser mayor a 0")
    private BigDecimal precio;

    private List<StockRequest> stocks;

    @NotNull(message = "SeControlaStock no puede ser null")
    private Boolean seControlaStock;
}
