package bo.com.micrium.modulobase.models;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Entity(name = "MU_ACCION")
public class Accion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private Long id;
    private Long formularioId;
    private String nombre;
    private String url;
    private String metodo;

}
