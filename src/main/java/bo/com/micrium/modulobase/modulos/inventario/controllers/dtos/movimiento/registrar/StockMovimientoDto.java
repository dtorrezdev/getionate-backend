package bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class StockMovimientoDto {
    private Long id;
    private String lote;
    private Date expiracion;
    private Long ubicacionStockId;
    private Integer cantidad;
    private String registroSanitario;
}
