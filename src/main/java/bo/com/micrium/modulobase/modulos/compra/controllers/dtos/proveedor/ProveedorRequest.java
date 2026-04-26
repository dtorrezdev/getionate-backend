package bo.com.micrium.modulobase.modulos.compra.controllers.dtos.proveedor;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProveedorRequest implements Serializable {
    private String id;
    private String nombre;
    private String descripcion;
}

