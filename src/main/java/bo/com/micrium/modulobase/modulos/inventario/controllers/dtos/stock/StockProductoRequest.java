package bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class StockProductoRequest implements Serializable {
    private Long id;
    private Integer cantidad;
}
