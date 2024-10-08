package bo.com.micrium.modulobase.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author alepaco.maton
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "MU_GRUPO_AD")
public class Grupo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "MU_GRUPO_AD_GRUPOID_GENERATOR", sequenceName = "SEQ_MU_GRUPO_AD", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MU_GRUPO_AD_GRUPOID_GENERATOR")
    private Long id;
    private String nombre;
    private String descripcion;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ROL_ID")
    private Rol rolId;
    @JsonIgnore
    private Boolean estado;

}
