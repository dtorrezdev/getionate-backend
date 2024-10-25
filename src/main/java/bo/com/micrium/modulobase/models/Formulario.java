package bo.com.micrium.modulobase.models;

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
 *
 * @author alepaco.maton
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "MU_FORMULARIO")
public class Formulario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "formulario_seq")
    @SequenceGenerator(name = "formulario_seq", sequenceName = "SEQ_MU_FORMULARIO", allocationSize = 1)
    private Long id;
    private String nombre;
    private Integer orden;
    private Long moduloId;
    private String url;
    private String icono;

}
