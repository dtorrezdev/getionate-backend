package bo.com.micrium.modulobase.modulos.compra.Mappers;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.crear.CompraRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.crear.CompraResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.crear.DetalleCompraRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.get.GetOrdenCompraResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.get.GetDetalleCompraResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.list.OrdenCompraResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.update.CompraUpdateRequest;
import com.micrium.bd.access.jpa.modulo.compra.models.Compra;
import com.micrium.bd.access.jpa.modulo.compra.models.DetalleCompra;
import com.micrium.bd.access.jpa.modulo.compra.projection.ListCompraProjection;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrdenCompraMapper {

    public static final Function<DetalleCompraRequest, DetalleCompra> toDetalleEntity
            = dto ->
            DetalleCompra.builder()
                    .presentacionId(dto.getPresentacionId())
                    .productoId(dto.getProductoId())
                    .cantidadRecibido(dto.getCantidad())
//                    .cantidadSolicitado(dto.getCantidad())
                    .precio(dto.getPrecio())
                    .build();

    public static final Function<CompraRequest, Compra> toEntity = request -> {

        Compra compra = new Compra();
//        compra.setId(null);
        compra.setFechaCompra(new Timestamp(System.currentTimeMillis()));
        compra.setEstado(request.getEstado());
        compra.setTipoCompra(request.getTipo());
        compra.setTotal(request.getTotal());
        compra.setGlosa(request.getGlosa());
        compra.setCodigoCompra(request.getCodigo());
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
        response.setCodigo(compra.getCodigoCompra());
        return response;
    };

    public static final Function<ListCompraProjection, OrdenCompraResponse>
            fromProjectionToListCompraResponse = compra -> {
        OrdenCompraResponse response = new OrdenCompraResponse();
        response.setId(compra.getId());
        response.setProveedor(compra.getProveedor());
        response.setEstado(compra.getEstado());
        response.setCodigo(compra.getCodigoCompra());
        response.setGlosa(compra.getGlosa());
        response.setTotal(compra.getTotal());
        response.setTipoCompra(compra.getTipoCompra());
        response.setSolicitante(compra.getSolicitante());
        response.setAprobador(compra.getAprobador());
        response.setProvedorId(compra.getProveedorId());
        response.setFecha(compra.getFechaCompra());
        response.setNroItems(compra.getNroItems());
        return response;
    };

    // Update Compra Response
    public static final Function<CompraUpdateRequest, Compra> fromUpdatetoEntity = request -> {

        Compra compra = new Compra();
        compra.setId(compra.getId());
        compra.setFechaSolicitud(new Timestamp(System.currentTimeMillis()));
        compra.setTipoCompra(request.getTipo());
        compra.setEstado(request.getEstado());
        compra.setTotal(request.getTotal());
        compra.setGlosa(request.getGlosa());
        compra.setCodigoCompra(request.getCodigo());
        compra.setProveedorId(request.getProveedorId());

        List<DetalleCompra> detalles = request.getDetalle().stream()
                .map(toDetalleEntity)
                .collect(Collectors.toList());

        compra.setDetalle(detalles);

        detalles.forEach(d -> d.setCompra(compra));

        return compra;
    };


    // Get Compra Response
    public static final Function<Compra, GetOrdenCompraResponse> fromEntityToGetCompraResponse = compra -> {
        GetOrdenCompraResponse response = new GetOrdenCompraResponse();
        response.setId(compra.getId());
        response.setProveedorId(compra.getProveedorId());
        response.setEstado(compra.getEstado());
        response.setCodigo(compra.getCodigoCompra());
        response.setGlosa(compra.getGlosa());
        response.setTotal(compra.getTotal());
        response.setProveedorId(compra.getProveedorId());
        response.setFecha(compra.getFechaCompra());
        response.setTipoCompra(compra.getTipoCompra());

        List<GetDetalleCompraResponse> detalles = compra.getDetalle().stream()
                .map(detalle -> {
                    GetDetalleCompraResponse detalleResponse = new GetDetalleCompraResponse();
                    detalleResponse.setProductoId(detalle.getProductoId());
                    detalleResponse.setPresentacionId(detalle.getPresentacionId());
                    detalleResponse.setCantidad(detalle.getCantidadRecibido());
                    detalleResponse.setPrecio(detalle.getPrecio());
                    return detalleResponse;
                }).collect(Collectors.toList());

        response.setDetalle(detalles);

        return response;
    };
}
