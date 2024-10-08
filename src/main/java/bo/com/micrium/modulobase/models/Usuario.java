package bo.com.micrium.modulobase.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author alepaco.maton
 */
@ToString(exclude = "contrasena")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "mu_usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1;
    
    @Id
    @SequenceGenerator(name = "USUARIO_GENERATOR", sequenceName = "SEQ_MU_USUARIO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_GENERATOR")
    private Long id;
    private String nombreUsuario;
    private String nombreCompleto;
    @JsonIgnore
    private String contrasena;
    private Integer tipo;
    @JsonIgnore
    private short numeroIntentos;
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimoIntento;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "ROL_ID")
    private Rol rolId;
    private short estado;
    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
}
