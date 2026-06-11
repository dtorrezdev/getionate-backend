package bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StockUpdateRequest implements Serializable {
    private List<StockDisponibleDto> stocks;
    private Long ubicacionStockId;
}
