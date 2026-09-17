package com.dtorrez.main.modulos.producto.mappers;

import com.dtorrez.main.common.enums.EnumInventario;
import com.dtorrez.main.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionRequest;
import com.dtorrez.main.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionResponse;
import com.dtorrez.main.modulos.producto.controllers.dtos.producto_presentacion.list.ListPresentacionResponse;
import com.micrium.bd.access.jpa.modulo.productos.models.ProductoPresentacion;
import com.micrium.bd.access.jpa.modulo.productos.projection.ListPresentacionProjection;

import java.util.function.Function;

public class ProductoPresentacionMapper {

    public static final Function<ProductoPresentacionRequest, ProductoPresentacion>
        fromRequestToEntity = request ->
        ProductoPresentacion.builder()
//                .id(null)
                .productoId(request.getProductoId())
                .codigo(request.getCodigo())
                .concepto(request.getConcepto())
                .descripcion(request.getDescripcion())
                .precioUnitario(request.getPrecioUnitario())
                .precioVenta(request.getPrecioVenta())
                .unidadMedidaId(request.getUnidadMedidaId())
                .marcaId(request.getMarcaId())
                .nombre(request.getNombre())
                .cantidadDisponibleStock(request.getCantidadDisponibleStock())
                .cantidadMinimoStock(request.getCantidadMinimoStock())
                .diasAntesExpiracion(request.getDiasAntesExpiracion())
                .esActivo(Boolean.TRUE)
                .seControlaStock(request.getSeControlaStock())
                .imagen(request.getImagen())
                .build()
     ;

    public static final Function<ProductoPresentacion, ProductoPresentacionResponse>
            toResponse = entity -> {
        ProductoPresentacionResponse response = new ProductoPresentacionResponse();
        response.setId(entity.getId());
        response.setProductoId(entity.getProductoId());
        response.setCodigo(entity.getCodigo());
        response.setNombre(entity.getNombre());
        response.setConcepto(entity.getConcepto());
        response.setDescripcion(entity.getDescripcion());
        response.setPrecioUnitario(entity.getPrecioUnitario());
        response.setPrecioVenta(entity.getPrecioVenta());
        response.setUnidadMedidaId(entity.getUnidadMedidaId());
        response.setMarcaId(entity.getMarcaId());
        response.setCantidadDisponibleStock(entity.getCantidadDisponibleStock());
        response.setCantidadMinimoStock(entity.getCantidadMinimoStock());
        response.setDiasAntesExpiracion(entity.getDiasAntesExpiracion());
        response.setSeControlaStock(entity.getSeControlaStock());
        response.setImagen(entity.getImagen());
        return response;
    };

    public static final Function<ListPresentacionProjection, ListPresentacionResponse>
            fromProjectionToListPresentacionResponse = entity -> {
        ListPresentacionResponse response = new ListPresentacionResponse();
        response.setProductoId(entity.getProductoId());
        response.setProducto(entity.getProducto());
        response.setId(entity.getId());
        response.setCodigo(entity.getCodigo());
        response.setPresentacion(entity.getPresentacion());
        response.setPresentacionLarga(entity.getPresentacionLarga());
        response.setPrincipioActivo(entity.getPrincipioActivo());
        response.setUnidadMedidaId(entity.getUnidadMedidaId());
        response.setUnidadMedidaShort(entity.getUnidadMedidaShort());
        response.setUnidadMedida(entity.getUnidadMedida());
        response.setMarcaId(entity.getMarcaId());
        response.setMarca(entity.getMarca());
        response.setPrecioVenta(entity.getPrecioVenta());
        response.setPrecioUnitario(entity.getPrecioUnitario());
        response.setCategoriaId(entity.getCategoriaId());
        response.setCategoria(entity.getCategoria());
        response.setCantidadMinimoStock(entity.getCantidadMinimoStock());
        response.setCantidadDisponibleStock(entity.getCantidadDisponibleStock());
        response.setDiasAntesExpiracion(entity.getDiasAntesExpiracion());
        response.setSeControlaStock(entity.getSeControlaStock());
        response.setImagen(entity.getImagen());
        // no es buena practica poner logica del negocio en Mapper BAD
        if(!entity.getSeControlaStock()) {
            response.setEstadoStock(EnumInventario.StockStatus.NO_APLICA.name());
        }else if(entity.getCantidadDisponibleStock() <= 0) {
            response.setEstadoStock(EnumInventario.StockStatus.AGOTADO.name());
        } else
        if(entity.getCantidadDisponibleStock() <= entity.getCantidadMinimoStock()) {
            response.setEstadoStock(EnumInventario.StockStatus.POCO_STOCK.name());
        } else {
            response.setEstadoStock(EnumInventario.StockStatus.HAY_STOCK.name());
        }
        return response;
    };

    // Get
    public static final Function<ProductoPresentacion, ProductoPresentacionResponse>
            toGetResponse = entity -> {
        ProductoPresentacionResponse response = new ProductoPresentacionResponse();
        response.setId(entity.getId());
        response.setCodigo(entity.getCodigo());
        response.setProductoId(entity.getProductoId());
        response.setConcepto(entity.getConcepto());
        response.setDescripcion(entity.getDescripcion());
        response.setPrecioUnitario(entity.getPrecioUnitario());
        response.setPrecioVenta(entity.getPrecioVenta());
        response.setUnidadMedidaId(entity.getUnidadMedidaId());
        response.setMarcaId(entity.getMarcaId());
        response.setNombre(entity.getNombre());
        response.setCantidadDisponibleStock(entity.getCantidadDisponibleStock());
        response.setCantidadMinimoStock(entity.getCantidadMinimoStock());
        response.setDiasAntesExpiracion(entity.getDiasAntesExpiracion());
        response.setSeControlaStock(entity.getSeControlaStock());
        response.setImagen(entity.getImagen());
        return response;
    };
}
