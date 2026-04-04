package bo.com.micrium.modulobase.modulos.inventario.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class DetalleMovimientoRequest implements Serializable {
    private String lote;
    private Date fechaExpiracion;
    private Integer cantidadStock;
    private Integer cantidadStockBase;
    private String registroSanitario;
}
