package bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.list;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class ListMovimientoProductoRequest implements Serializable {
    private Timestamp fecha;
    private String tipoMovimiento;
    private String motivo;
    private String producto;
    private String desde;
    private String hasta;
    private String lote;
    private Integer cantidad;
    private String unidadMedida;
    private String unidadMedidaShort;
}
