package bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion;

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
public class AnularRecepcionProductoRequest implements Serializable {
    private Long recepcionProductoId;
    private String glosa;
    private Long proveedorId;
}

