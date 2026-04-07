package bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoPresentacionResponse implements Serializable {
    private Long id;
    private Long productoId;
    private String nombre;
    private String concepto;
    private String descripcion;
    private Long unidadMedidaId;
    private Boolean esUnidadMinima;
    private Integer factorConversion;
    private BigDecimal precioUnitario;
    private BigDecimal precioVenta;
    private Integer cantidadMinimoStock;
    private Integer cantidadDisponibleStock;
    private Integer diasAntesExpiracion;
    private Long marcaId;
}
