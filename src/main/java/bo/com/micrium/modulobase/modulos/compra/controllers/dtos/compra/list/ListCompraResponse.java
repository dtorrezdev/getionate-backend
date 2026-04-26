package bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.list;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ListCompraResponse implements Serializable {
    private Long id;
    private BigDecimal total;
    private String glosa;
    private Timestamp fecha;
    private Long provedorId;
    private String estado;
}

