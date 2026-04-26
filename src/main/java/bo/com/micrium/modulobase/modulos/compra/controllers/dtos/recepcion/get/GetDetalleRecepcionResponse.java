package bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetDetalleRecepcionResponse implements Serializable {
    private Long id;
    private Long presentacionId;
    private Long productoId;
    private Long compraId;
    private Date fechaVencimiento;
    private String lote;
    private Integer cantidad;
    private Double precio;
}

