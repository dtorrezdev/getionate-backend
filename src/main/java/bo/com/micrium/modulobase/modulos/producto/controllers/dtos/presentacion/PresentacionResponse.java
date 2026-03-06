package bo.com.micrium.modulobase.modulos.producto.controllers.dtos.presentacion;

import java.io.Serializable;

public class PresentacionResponse implements Serializable {
    private Long id;
    private String nombre;
    private String descripcion;
    private Long tipoId;
    private Long unidadMedidaId;
}
