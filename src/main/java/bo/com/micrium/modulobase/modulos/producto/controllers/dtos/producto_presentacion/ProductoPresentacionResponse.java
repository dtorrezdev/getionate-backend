package bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ProductoPresentacionResponse implements Serializable {
    private Long id;
    private Long productoId;
    private String nombre;
    private String concepto;
    private String descripcion;
    private Long unidadMedidaId;
    private BigDecimal precioUnitario;
    private BigDecimal precioVenta;
    private Integer cantidadMinimoStock;
    private Integer cantidadDisponibleStock;
    private Integer diasAntesExpiracion;
    private Long marcaId;
    private Boolean seControlaStock;
    private String imagen;
}
