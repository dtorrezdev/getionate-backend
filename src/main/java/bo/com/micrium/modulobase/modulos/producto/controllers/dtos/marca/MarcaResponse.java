package bo.com.micrium.modulobase.modulos.producto.controllers.dtos.marca;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class MarcaResponse implements Serializable {
    private Long id;
    private String nombre;
    private String descripcion;
}


