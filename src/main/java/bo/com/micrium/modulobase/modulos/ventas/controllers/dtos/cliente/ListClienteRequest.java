package bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.cliente;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class ListClienteRequest implements Serializable {
    private Long id;
    private String ci;
    private String nombre;
    private String celular;
}
