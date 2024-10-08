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
 * @author alepaco.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "log_sistema")
public class LogSistema implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "seq_log_sistema", sequenceName = "seq_log_sistema", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_log_sistema")
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    private String app;
    private String proceso;
    private String detalle;
    private String nivel;
    private String trazabilidad;

}
