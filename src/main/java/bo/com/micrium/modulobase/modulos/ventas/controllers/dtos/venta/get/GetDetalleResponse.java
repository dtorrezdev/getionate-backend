package bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.get;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class GetDetalleResponse implements Serializable {
    private Long id;
    private Long presentacionId;
    private Long productoId;
    private Integer cantidad;
    private Integer cantidadBase;
    private BigDecimal precio;
    private BigDecimal subtotal;
}
