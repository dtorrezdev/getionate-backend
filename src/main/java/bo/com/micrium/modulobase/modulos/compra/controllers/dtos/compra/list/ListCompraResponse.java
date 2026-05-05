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
    private Timestamp fechaCompra;
    private Timestamp fechaSolicitud;
    private Long provedorId;
    private String proveedor;
    private String estado;
    private Integer nroItems;
    private String codigo;
}
