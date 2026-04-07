package bo.com.micrium.modulobase.modulos.inventario.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class MovimientoResponse implements Serializable {
    private Long id;
}
