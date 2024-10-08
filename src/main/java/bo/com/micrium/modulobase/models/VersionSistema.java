package bo.com.micrium.modulobase.models;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Entity(name = "version_sistema")
public class VersionSistema implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String version;
    private String resumen;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha; 

}
