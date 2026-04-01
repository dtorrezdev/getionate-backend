package bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.get;

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
public class GetVentaResponse implements Serializable {
    private Long id;
    private Double total;
    private String codigo;
    private String glosa;
    private Long clienteId;
    private String estado;  // PREVENTA, ANULADA, VENTA
    private Long movimientoId;
    private List<GetDetalleResponse> detalle;
}
