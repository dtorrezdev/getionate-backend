package bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class ProductoResponse implements Serializable {

    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private Long categoriaId;
}
