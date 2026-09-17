package com.dtorrez.main.modulos.producto.controllers.dtos.producto_presentacion;

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

    @NotNull(message = "Codigo no puede ser nulo.")
    private String codigo;

    @NotNull(message = "Nombre Presentacion no puede ser nulo.")
    @NotEmpty(message = "Nombre Presentacion no puede ser vacio.")
    @Size(max = 60, message = "Nombre no debe exceder de 60 letras.")
    private String nombre;

    @Size(max = 255, message = "Principo activos no debe exceder de 255 letras.")
    private String concepto; // principio activos
    @Size(max = 255, message = "Descripcion no debe exceder de 255 letras.")
    private String descripcion;

    @NotNull(message = "Unidad Medida Id no puede ser nulo.")
    private Long unidadMedidaId;

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

    @NotNull(message = "Se Controla Stock no puede ser nulo.")
    private Boolean seControlaStock;

    @Size(max = 255, message = "Imagen no debe exceder de 255 letras.")
    private String imagen;

}
