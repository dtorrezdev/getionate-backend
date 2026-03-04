package bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class ProductoResponse implements Serializable {

    private Long productoId;
    private String codigo;
    private String nombre;
    private String descripcion;

}
