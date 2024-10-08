package bo.com.micrium.modulobase.models;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Entity(name = "MU_BITACORA")
public class Bitacora implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "BITACORA_GENERATOR", sequenceName = "SEQ_MU_BITACORA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BITACORA_GENERATOR")
    private Long id;
    private String accion;
    private String direccionIp;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    private String formulario;
    private String usuario;
    private String valorAnterior;
    private String valorNuevo;
    private Long logSistemaId;

}
