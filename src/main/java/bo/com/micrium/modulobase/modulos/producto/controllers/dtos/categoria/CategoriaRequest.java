package bo.com.micrium.modulobase.modulos.producto.controllers.dtos.categoria;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoriaRequest implements Serializable {
    private String id;
    private String nombre;
    private String descripcion;
}
