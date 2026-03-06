package bo.com.micrium.modulobase.modulos.producto.controllers.dtos.presentacion;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class PresentacionRequest implements Serializable {
    private String nombre;
    private String descripcion;
    private Long tipoId;
    private Long unidadMedidaId;
}
