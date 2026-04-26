package bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.list;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
@ToString
public class ListRecepcionProductoRequest implements Serializable {

    private String id;
    private String total;
    private String glosa;
    private String fecha;
    private String proveedorId;
    private String movimientoId;
}

