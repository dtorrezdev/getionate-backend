package bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoPresentacionRequest implements Serializable {
    @NotNull(message = "Producto Id no puede ser nulo.")
    private Long productoId;

    @NotNull(message = "Nombre Presentacion no puede ser nulo.")
    @NotEmpty(message = "Nombre Presentacion no puede ser vacio.")
    @Size(max = 60, message = "Nombre no debe exceder de  100 letras.")
    private String nombre;

    private String concepto; // principio activos
    private String descripcion;

    @NotNull(message = "Unidad Medida Id no puede ser nulo.")
    private Long unidadMedidaId;

    @NotNull(message = "Es unidad minima no puede ser nulo.")
    private Boolean esUnidadMinima;

    @NotNull(message = "Factor Conversion no puede ser nulo.")
    private Integer factorConversion;

    @NotNull(message = "Precio Unitario no puede ser nulo.")
    private BigDecimal precioUnitario;

    @NotNull(message = "Precio Venta no puede ser nulo.")
    private BigDecimal precioVenta;

    @NotNull(message = "Cantidad Disponible Stock no puede ser nulo.")
    public Integer cantidadDisponibleStock;

    @NotNull(message = "Cantidad Minima Stock no puede ser nulo.")
    public Integer cantidadMinimoStock;

    @NotNull(message = "Dias antes Expiracion no puede ser nulo.")
    public Integer diasAntesExpiracion;

    @NotNull(message = "Marca Id no puede ser nulo.")
    private Long marcaId;
}
