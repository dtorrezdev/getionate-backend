package bo.com.micrium.modulobase.modulos.producto.controllers.dtos.unidad_medida;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class UnidadMedidaRequest implements Serializable {
    private String codigo;
    private String nombre;
    private String descripcion;
}