package bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetDetalleCompraResponse implements Serializable {
//    private Long id;
    private Long presentacionId;
    private Long productoId;
//    private Long compraId;
    private Integer cantidad;
    private BigDecimal precio;
}

