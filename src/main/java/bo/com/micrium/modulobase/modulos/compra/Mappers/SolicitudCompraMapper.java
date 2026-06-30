package bo.com.micrium.modulobase.modulos.compra.Mappers;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.crear.CompraRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.crear.CompraResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.crear.DetalleCompraRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.list.OrdenCompraResponse;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.solicitud.SolicitudCompraResponse;
import com.micrium.bd.access.jpa.modulo.compra.models.Compra;
import com.micrium.bd.access.jpa.modulo.compra.models.DetalleCompra;
import com.micrium.bd.access.jpa.modulo.compra.projection.ListCompraProjection;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SolicitudCompraMapper {

    public static final Function<DetalleCompraRequest, DetalleCompra> toDetalleEntity
            = dto ->
            DetalleCompra.builder()
                    .presentacionId(dto.getPresentacionId())
                    .productoId(dto.getProductoId())
//                    .cantidadRecibido(dto.getCantidad())
                    .cantidadSolicitado(dto.getCantidad())
                    .precio(dto.getPrecio())
                    .build();

    public static final Function<CompraRequest, Compra> toEntity = request -> {

        Compra compra = new Compra();
//        compra.setId(null);
        compra.setFechaSolicitud(new Timestamp(System.currentTimeMillis()));
        compra.setEstado(request.getEstado());
        compra.setTotal(request.getTotal());
        compra.setGlosa(request.getGlosa());
        compra.setCodigoSolicitud(request.getCodigo());

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
        response.setCodigo(compra.getCodigoSolicitud());
        return response;
    };

    public static final Function<ListCompraProjection, SolicitudCompraResponse>
            fromProjectionToSolicitudCompraResponse = compra -> {
        SolicitudCompraResponse response = new SolicitudCompraResponse();
        response.setId(compra.getId());
        response.setEstado(compra.getEstado());
        response.setCodigo(compra.getCodigoSolicitud());
        response.setGlosa(compra.getGlosa());
        response.setTotal(compra.getTotal());
        response.setSolicitante(compra.getSolicitante());
        response.setAprobador(compra.getAprobador());
        response.setFecha(compra.getFechaSolicitud());
        response.setNroItems(compra.getNroItems());
        return response;
    };
}
