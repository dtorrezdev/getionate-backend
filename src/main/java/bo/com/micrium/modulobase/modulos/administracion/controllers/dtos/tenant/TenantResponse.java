package bo.com.micrium.modulobase.modulos.administracion.controllers.dtos.tenant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class TenantResponse implements Serializable {
    private Long id;
    private String nombre;
    private String logoUrl;
    private String direccion;
    private String celular;
    private String ciudad;
}
