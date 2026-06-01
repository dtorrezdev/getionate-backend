package bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.get;

import bo.com.micrium.modulobase.modulos.administracion.controllers.dtos.tenant.TenantResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class GetNotaVentaResponse implements Serializable {
    private TenantResponse tenant;
    private HeaderVentaResponse header;
    private List<DetalleVentaResponse> detalle;
}
