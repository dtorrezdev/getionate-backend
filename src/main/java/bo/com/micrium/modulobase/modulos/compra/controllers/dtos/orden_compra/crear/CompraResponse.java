package bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.crear;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
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
    private String codigo;
}

