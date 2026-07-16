package bo.com.micrium.modulobase.modulos.promo.controllers.dtos.uso;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class UsoResponse implements Serializable {
    private Long id;
    private Long promocionId;
    private Long clienteId;
    private Long ventaId;
    private BigDecimal cantidadDescuento;
}
