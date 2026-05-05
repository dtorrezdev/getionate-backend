package bo.com.micrium.modulobase.modulos.compra.Mappers;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.crear.CompraRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.crear.CompraResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.crear.DetalleCompraRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.list.ListCompraResponse;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.list.ListVentaResponse;
import com.micrium.bd.access.jpa.modulo.compra.models.Compra;
import com.micrium.bd.access.jpa.modulo.compra.models.DetalleCompra;
import com.micrium.bd.access.jpa.modulo.compra.projection.ListCompraProjection;
import com.micrium.bd.access.jpa.modulo.venta.projection.ListVentaProjection;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CompraMapper {

    public static final Function<DetalleCompraRequest, DetalleCompra> toDetalleEntity
            = dto ->
            DetalleCompra.builder()
                    .presentacionId(dto.getPresentacionId())
                    .productoId(dto.getProductoId())
                    .cantidad(dto.getCantidad())
                    .precio(dto.getPrecio())
                    .build();

    public static final Function<CompraRequest, Compra> toEntity = request -> {

        Compra compra = new Compra();
//        compra.setId(null);
        compra.setFechaSolicitud(new Timestamp(System.currentTimeMillis()));
        compra.setEstado(request.getEstado());
        compra.setTotal(request.getTotal());
        compra.setGlosa(request.getGlosa());
        compra.setCodigo(request.getCodigo());
        compra.setProveedorId(request.getProveedorId());

        List<DetalleCompra> detalles = request.getDetalle().stream()
                .map(toDetalleEntity)
                .collect(Collectors.toList());

        compra.setDetalle(detalles);

        detalles.forEach(d -> d.setCompra(compra));

        return compra;
    };

    public static final Function<Compra, CompraResponse> toResponse = compra -> {
        CompraResponse response = new CompraResponse();
        response.setId(compra.getId());
        response.setMensaje("Compra creado exitosamente.");
        return response;
    };

    public static final Function<ListCompraProjection, ListCompraResponse>
            fromProjectionToListCompraResponse = compra -> {
        ListCompraResponse response = new ListCompraResponse();
        response.setId(compra.getId());
        response.setProveedor(compra.getProveedor());
        response.setEstado(compra.getEstado());
        response.setCodigo(compra.getCodigo());
        response.setGlosa(compra.getGlosa());
        response.setTotal(compra.getTotal());
        response.setProvedorId(compra.getProveedorId());
        response.setFechaCompra(compra.getFechaCompra());
        response.setFechaSolicitud(compra.getFechaSolicitud());
        response.setNroItems(compra.getNroItems());
        return response;
    };

}
