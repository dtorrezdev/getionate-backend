package bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoPresentacionRequest implements Serializable {
    private Long productoId;
    private String nombre;
    private String concepto;
    private String descripcion;
    private Long tipoId;
    private String unidadMedida;
    private Double precioRef;
    private Double precioVenta;
    private Double precioXMayor;
}
