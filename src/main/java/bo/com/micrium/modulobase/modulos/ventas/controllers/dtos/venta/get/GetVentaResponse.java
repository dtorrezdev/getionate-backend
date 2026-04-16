package bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.get;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.pago.DetallePagoResponse;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.pago.PagoResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class GetVentaResponse implements Serializable {
    private Long id;
    private BigDecimal total;
    private String codigo;
    private String glosa;
    private Timestamp fechaRegistro;
    private Long clienteId;
    private String estado;  // PREVENTA, ANULADA, VENTA
    private Long movimientoId;
    private List<GetDetalleResponse> detalle;
    private List<DetallePagoResponse> pagos;
}
