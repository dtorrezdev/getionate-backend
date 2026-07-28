package bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoListRequest implements Serializable {
    private String codigo;
    private String nombre;
    private String descripcion;
    private String categoria;
}
