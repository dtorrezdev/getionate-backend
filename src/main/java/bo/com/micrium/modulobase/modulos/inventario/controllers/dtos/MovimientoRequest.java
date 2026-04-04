package bo.com.micrium.modulobase.modulos.inventario.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class MovimientoRequest implements Serializable {
    private Long tipoMovimientoId;
    private String motivo;
    private Long productoId;
    private Long presentacionId;
    private Long ubicacionStockId;
    private List<DetalleMovimientoRequest> detalleMovimiento;
}
