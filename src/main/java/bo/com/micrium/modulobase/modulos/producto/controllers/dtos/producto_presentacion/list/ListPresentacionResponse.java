package bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.list;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
@ToString
public class ListPresentacionResponse implements Serializable {
    private Long productoId;
    private String producto;
    private Long id;
    private String presentacion;
    private String presentacionLarga;
    private String principioActivo;
    private Long unidadMedidaId;
    private String unidadMedida;
    private String unidadMedidaShort;
    private Long marcaId;
    private String marca;
    private BigDecimal precioVenta;
    private Long categoriaId;
    private String categoria;
    private Integer cantidadMinimoStock;
    private Integer cantidadDisponibleStock;
}
