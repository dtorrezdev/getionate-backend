package bo.com.micrium.modulobase.modulos.producto.controllers.dtos.marca;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class MarcaRequest implements Serializable {
    private String nombre;
    private String descripcion;
}
