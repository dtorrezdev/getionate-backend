package bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovimientoRequest implements Serializable {
    @NotNull( message = "Tipo Movimiento Id no puede ser nulo.")
    private Long tipoMovimientoId;

    @NotNull( message = "Motivo movimiento no puede ser nulo.")
    @NotEmpty( message = "Motivo movimiento no puede ser vacio.")
    private String motivo;

    @NotNull( message = "Producto Id no puede ser nulo.")
    private Long productoId;

    @NotNull( message = "Presentacion Id no puede ser nulo.")
    private Long presentacionId;

//    @NotNull( message = "Ubicacion Stock Id no puede ser nulo.")
    private Long ubicacionStockId;

    @NotEmpty(message = "Debe existir al menos un fila en detalle")
    @Valid
    private List<DetalleMovimientoRequest> detalleMovimiento;
}
