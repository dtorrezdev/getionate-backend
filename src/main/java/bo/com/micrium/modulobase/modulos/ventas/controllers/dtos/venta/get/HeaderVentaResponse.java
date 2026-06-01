package bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.get;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class HeaderVentaResponse implements Serializable {
    private Long id;
    private BigDecimal total;
    private Timestamp fechaRegistro;
    private String codigo;
    private String estado;
    private Long clienteId;
    private String cliente;
}
