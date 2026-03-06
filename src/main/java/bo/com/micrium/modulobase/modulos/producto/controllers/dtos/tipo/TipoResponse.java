package bo.com.micrium.modulobase.modulos.producto.controllers.dtos.tipo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class TipoResponse implements Serializable {
    private Long tipoId;
    private String nombre;
    private String descripcion;
}
