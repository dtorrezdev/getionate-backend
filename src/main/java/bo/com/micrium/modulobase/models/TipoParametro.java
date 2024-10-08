package bo.com.micrium.modulobase.models;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author alepaco.maton
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "MU_TIPO_PARAMETRO")
public class TipoParametro implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Long TIPO_PARAMETRO_LDAP = 1L;
    public static final Long TIPO_PARAMETRO_SISTEMA = 3L;
  
    @Id
    private Long id;
    private String nombre;
    private String descripcion;

}
