package bo.com.micrium.modulobase.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author alepaco.maton
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "MU_ROL")
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;   
    
    public static final Long SUPER_ADMINISTRADOR = 1L;

    @Id
    @SequenceGenerator(name = "ROL_GENERATOR", sequenceName = "SEQ_MU_ROL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROL_GENERATOR")
    private Long id;
    private String nombre;
    private String descripcion;
    @JsonIgnore
    private Boolean estado = true;

}
