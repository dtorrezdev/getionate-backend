package bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.pago;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PagoRequest implements Serializable {

    @Min(value = 1, message = "total pago debe ser mayor a 0")
    private BigDecimal totalPago;

    private Long ventaId;

    @Valid
    List<DetallePagoRequest> detallePago;
}
