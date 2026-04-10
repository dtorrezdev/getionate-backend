package bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.venta;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
public class MovimientoVentaRequest implements Serializable {
    private Long tipoMovimientoId;
    private String motivo;
    private List<DetalleMovimientoVentaRequest> detalleMovimiento;
}
