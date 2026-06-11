package bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class StockDisponibleDto {
    private Long id; // stock_id
    private String lote;
    private Date expiracion;
    private Integer cantidad;
    private Long ubicacionStockId;
    private String seccion;
    private String estante;
    private String nivel;
    private Long presentacionId;
    private String registroSanitario;
//    private String estadoExpiracion; // VIGENTE, POR_VENCER, VENCIDO
}
