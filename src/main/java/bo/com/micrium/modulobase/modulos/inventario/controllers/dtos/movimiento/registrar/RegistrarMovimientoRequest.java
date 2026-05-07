package bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RegistrarMovimientoRequest {
    @NotNull( message = "Tipo Movimiento Id no puede ser nulo.")
    private Long tipoMovimientoId;
    @NotNull( message = "Motivo movimiento no puede ser nulo.")
    @NotEmpty( message = "Motivo movimiento no puede ser vacio.")
    private String motivo;
    @NotNull( message = "Motivo movimiento no puede ser nulo.")
    private Timestamp fecha;

    @NotEmpty(message = "Debe existir al menos un fila en itemMovimientos")
    @Valid
    private List<ItemMovimientoDto> itemMovimientos;

}
