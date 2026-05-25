package bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ItemMovimientoDto {
    @NotNull( message = "Producto Id no puede ser nulo.")
    private Long productoId;

    @NotNull( message = "Presentacion Id no puede ser nulo.")
    private Long presentacionId;

    @NotNull(message = "Cantidad Stock no puede ser nulo.")
    @Min(value = 1, message = "Cantidad Stock debe ser mayor a 0")
    private Integer cantidad;
    private List<StockMovimientoDto> stocks;
}
