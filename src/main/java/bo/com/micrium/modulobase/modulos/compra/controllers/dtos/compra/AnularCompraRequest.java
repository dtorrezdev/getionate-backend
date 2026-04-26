package bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra;

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
public class AnularCompraRequest implements Serializable {
    private Long compraId;
    private String glosa;
    private Long provedorId;
}

