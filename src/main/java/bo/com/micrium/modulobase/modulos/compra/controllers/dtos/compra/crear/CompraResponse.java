package bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.crear;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
@ToString
public class CompraResponse implements Serializable {

    private Long id;
    private String mensaje;
}

