package bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.crear;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class CompraRequest implements Serializable {
    @NotNull(message = "total no puede ser null")
    @Min(value = 1, message = "total debe ser mayor a 0")
    private BigDecimal total;

    @NotNull(message = "proveedor Id no puede ser null")
    private Long provedorId;

    private String glosa;

    private String estado;

    @NotEmpty(message = "Debe existir al menos una fila en detalle")
    @Valid
    private List<DetalleCompraRequest> detalle;
}

