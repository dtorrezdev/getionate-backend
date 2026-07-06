package bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.update;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChangeStateRequest {
    Long id;
    private String estado;
}
