package bo.com.micrium.modulobase.modulos.promo.controllers.dtos.target;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class TargetRequest implements Serializable {
    private Long promocionId;
    private Long presentacionId;
    private Long categoriaId;
    private Long marcaId;
}
