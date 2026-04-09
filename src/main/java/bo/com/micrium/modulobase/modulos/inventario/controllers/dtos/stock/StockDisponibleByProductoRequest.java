package bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
@ToString
public class StockDisponibleByProductoRequest implements Serializable {
    private Long productoId;
    private Long presentacionId;

}
