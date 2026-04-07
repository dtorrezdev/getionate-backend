package bo.com.micrium.modulobase.modulos.inventario.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class DetalleMovimientoRequest implements Serializable {
    @NotNull(message = "Lote no puede ser nulo.")
    @NotEmpty(message = "Lote no puede ser vacio.")
    private String lote;

    @Future(message = "La Fecha Expitacion debe ser futura.")
    private Date fechaExpiracion;

    @NotNull(message = "Cantidad Stock no puede ser nulo.")
    @Min(value = 1, message = "Cantidad Stock debe ser mayor a 0")
    private Integer cantidadStock;

    @NotNull(message = "Cantidad Stock Base no puede ser nulo.")
    @Min(value = 1, message = "Cantidad Stock Base debe ser mayor a 0")
    private Integer cantidadStockBase;

    private String registroSanitario;
}
