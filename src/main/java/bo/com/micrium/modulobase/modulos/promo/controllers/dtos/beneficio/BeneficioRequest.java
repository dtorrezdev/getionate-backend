package bo.com.micrium.modulobase.modulos.promo.controllers.dtos.beneficio;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class BeneficioRequest implements Serializable {

    private Long promocionId;
    private String tipo;
    private String valor;
    private BigDecimal maximoDescuento;
}
