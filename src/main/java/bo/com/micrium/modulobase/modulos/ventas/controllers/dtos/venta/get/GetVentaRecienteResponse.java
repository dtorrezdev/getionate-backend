package bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.get;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class GetVentaRecienteResponse implements Serializable {
    private String imagen;
    private String producto;
    private BigDecimal subtotal;
    private Long ventaId;
}
