package bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = false)
@ToString
public class ListVentaRequest implements Serializable {
    private Long id;
    private String glosa;

}
