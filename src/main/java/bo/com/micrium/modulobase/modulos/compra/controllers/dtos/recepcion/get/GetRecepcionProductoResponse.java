package bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.get;

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
public class GetRecepcionProductoResponse implements Serializable {
    private Long id;
    private String codigo;
    private BigDecimal total;
    private Timestamp fecha;
    private String glosa;
    private Long compraId;
    private Long movimientoId;
    private List<GetDetalleRecepcionResponse> detalle;
}

