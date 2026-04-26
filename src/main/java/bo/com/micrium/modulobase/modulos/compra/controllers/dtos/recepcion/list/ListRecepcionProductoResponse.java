package bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.list;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ListRecepcionProductoResponse implements Serializable {
    private Long id;
    private BigDecimal total;
    private String glosa;
    private Timestamp fecha;
    private Long proveedorId;
    private Long movimientoId;
}

