package bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.list;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ListVentaResponse implements Serializable {
    private Long id;
    private String codigo;
    private String glosa;
    private BigDecimal total;
    private Timestamp fechaRegistro;
    private String cliente;
    private String estado;
    private Long movimientoId;
    private Long clienteId;
}
