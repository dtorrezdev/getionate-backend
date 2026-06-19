package bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.list;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
@ToString
public class ListCompraRequest implements Serializable {

    private String id;
    private String total;
    private String codigo;
    private String glosa;
    private String provedor;
    private String estado;
    private String fechaCompra;
    private String fechaSolicitud;
    private String tipo;
}

