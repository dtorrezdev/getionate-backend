package bo.com.micrium.modulobase.modulos.producto.controllers.dtos.marca;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class MarcaResponse implements Serializable {
    private Long marcaId;
    private String codigo;
    private String nombre;
    private String descripcion;
}


