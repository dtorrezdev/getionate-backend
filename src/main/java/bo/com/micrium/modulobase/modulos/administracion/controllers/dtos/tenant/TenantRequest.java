package bo.com.micrium.modulobase.modulos.administracion.controllers.dtos.tenant;

import lombok.Data;

import java.io.Serializable;

@Data
public class TenantRequest implements Serializable {
    private String id;
    private String nombre;
    private String logoUrl;
    private String direccion;
    private String celular;
    private String ciudad;
}
