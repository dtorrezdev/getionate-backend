package bo.com.micrium.modulobase.modulos.promo.controllers.dtos.target;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class TargetListRequest implements Serializable {
    private String promocionId;
    private String presentacionId;
    private String categoriaId;
    private String marcaId;
}
