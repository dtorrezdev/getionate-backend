package bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

public class VentaRequest implements Serializable {
    @NotNull(message = "Total no puede ser null")
    @Min(value = 1, message = "Total debe ser mayor a 0")
    private Double total;
    @NotNull(message = "Codigo no puede ser null")
    private String codigo;
    private String glosa;
    @NotNull(message = "Cliente Id no puede ser null")
    private Long clienteId;
    private String estado;  // PREVENTA, FINALIZADA

    private Long movimientoId;
    private ArrayList<DetalleVentaRequest> detalle;
}
