package bo.com.micrium.modulobase.modulos.producto.controllers.dtos.unidad_medida;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class UnidadMedidaResponse implements Serializable {
    private Long id;
    private String abreviatura;
    private String nombre;
    private Boolean esUnidadMinima;
}
