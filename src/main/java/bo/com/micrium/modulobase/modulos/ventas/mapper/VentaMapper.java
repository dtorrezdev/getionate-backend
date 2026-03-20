package bo.com.micrium.modulobase.modulos.ventas.mapper;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.DetalleVentaRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.VentaRequest;
import com.micrium.bd.access.jpa.modulo.venta.models.DetalleVenta;
import com.micrium.bd.access.jpa.modulo.venta.models.Venta;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VentaMapper {

    public Venta toEntity(VentaRequest dto) {

        Venta venta = new Venta();
        venta.setEstado(dto.getEstado());
        venta.setTotal(dto.getTotal());
        venta.setGlosa(dto.getGlosa());
        venta.setCodigo(dto.getCodigo());
        venta.setClienteId(dto.getClienteId());

        final List<DetalleVenta> detalle = dto.getDetalle().stream()
                .map(this::toDetalleEntity)
                .collect(Collectors.toList());

        venta.setDetalle(detalle);

        detalle.forEach(d -> d.setVenta(venta));

        return venta;
    }

    private DetalleVenta toDetalleEntity(DetalleVentaRequest dto) {

        DetalleVenta d = new DetalleVenta();
        d.setProductoId(dto.getProductoId());
        d.setCantidad(dto.getCantidad());

        return d;
    }

}
