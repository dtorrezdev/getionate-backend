package bo.com.micrium.modulobase.modulos.promo.controllers.dtos.promocion;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@AllArgsConstructor
@Data
public class PromocionRequest implements Serializable {
    private String nombre;
    private String descripcion;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    private Boolean isActive;
    private Integer limiteUso;
    private Integer limitePorCliente;
}
