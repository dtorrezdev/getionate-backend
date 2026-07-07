package bo.com.micrium.modulobase.modulos.compra.Mappers;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.get.GetDetalleCompraResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.get.GetOrdenCompraResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.crear.DetalleRecepcionRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.crear.RecepcionProductoRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.crear.RecepcionProductoResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.get.GetDetalleRecepcionResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.get.GetRecepcionProductoResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.list.ListRecepcionProductoResponse;
import com.micrium.bd.access.jpa.modulo.compra.models.Compra;
import com.micrium.bd.access.jpa.modulo.compra.models.DetalleRecepcion;
import com.micrium.bd.access.jpa.modulo.compra.models.RecepcionProducto;
import com.micrium.bd.access.jpa.modulo.compra.projection.ListRecepcionProjection;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RecepcionProductoMapper {

    public static final Function<DetalleRecepcionRequest, DetalleRecepcion> toDetalleEntity
            = dto ->
            DetalleRecepcion.builder()
                    .presentacionId(dto.getPresentacionId())
                    .productoId(dto.getProductoId())
                    .cantidad(dto.getCantidad())
                    .precio(dto.getPrecio())
                    .build();

    public static final Function<RecepcionProductoRequest, RecepcionProducto> toEntity = request -> {

        RecepcionProducto recepcion = new RecepcionProducto();
        recepcion.setFecha(new Timestamp(System.currentTimeMillis()));
        recepcion.setTotal(request.getTotal());
        recepcion.setGlosa(request.getGlosa());
        recepcion.setCompraId(request.getCompraId());
//        recepcion.setMovimientoId(request.getMovimientoId());
        recepcion.setCodigo(request.getCodigo());

        List<DetalleRecepcion> detalles = request.getDetalle().stream()
                .map(toDetalleEntity)
                .collect(Collectors.toList());

        recepcion.setDetalle(detalles);

        detalles.forEach(d -> d.setRecepcionProducto(recepcion));

        return recepcion;
    };

    public static final Function<RecepcionProducto, RecepcionProductoResponse> toResponse = recepcion -> {
        RecepcionProductoResponse response = new RecepcionProductoResponse();
        response.setId(recepcion.getId());
        return response;
    };

    public static final Function<ListRecepcionProjection, ListRecepcionProductoResponse>
            fromProjectionToListRecepcionResponse = recepcion -> {
        ListRecepcionProductoResponse response = new ListRecepcionProductoResponse();
        response.setId(recepcion.getId());
        response.setProveedor(recepcion.getProveedor());
        response.setProveedorId(recepcion.getProveedorId());
        response.setCodigo(recepcion.getCodigo());
        response.setGlosa(recepcion.getGlosa());
        response.setTotal(recepcion.getTotal());
        response.setFechaRegistro(recepcion.getFechaRegistro());
        response.setCompraId(recepcion.getCompraId());
        response.setCodigoCompra(recepcion.getCodigoCompra());
        response.setCompraEstado(recepcion.getCompraEstado());
        response.setNroItems(recepcion.getNroItems());

        return response;
    };

    // Get Compra Response
    public static final Function<RecepcionProducto, GetRecepcionProductoResponse> fromEntityToGetRecepcionResponse = recepcion -> {
        GetRecepcionProductoResponse response = new GetRecepcionProductoResponse();
        response.setId(recepcion.getId());
        response.setCompraId(recepcion.getCompraId());
        response.setGlosa(recepcion.getGlosa());
        response.setTotal(recepcion.getTotal());
        response.setFecha(recepcion.getFecha());
        response.setCodigo(recepcion.getCodigo());

        List<GetDetalleRecepcionResponse> detalles = recepcion.getDetalle().stream()
                .map(detalle -> {
                    GetDetalleRecepcionResponse detalleResponse = new GetDetalleRecepcionResponse();
                    detalleResponse.setProductoId(detalle.getProductoId());
                    detalleResponse.setPresentacionId(detalle.getPresentacionId());
                    detalleResponse.setCantidad(detalle.getCantidad());
                    detalleResponse.setPrecio(detalle.getPrecio());
                    return detalleResponse;
                }).collect(Collectors.toList());

        response.setDetalle(detalles);

        return response;
    };

}


