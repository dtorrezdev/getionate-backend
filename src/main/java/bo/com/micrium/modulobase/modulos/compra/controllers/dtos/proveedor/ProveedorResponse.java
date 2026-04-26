package bo.com.micrium.modulobase.modulos.compra.controllers.dtos.proveedor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class ProveedorResponse implements Serializable {
    private Long id;
    private String nombre;
    private String descripcion;
}

