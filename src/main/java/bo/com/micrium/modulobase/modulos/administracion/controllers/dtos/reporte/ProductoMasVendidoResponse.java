package bo.com.micrium.modulobase.modulos.administracion.controllers.dtos.reporte;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductoMasVendidoResponse implements Serializable {
    private String producto;
    private long cantidadVendida;
    private double porcentaje;
}
