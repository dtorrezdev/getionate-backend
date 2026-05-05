package bo.com.micrium.modulobase.modulos.producto.mappers;

import bo.com.micrium.modulobase.common.enums.EnumInventario;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.CreateProductoPresentacionResponse;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionResponse;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.list.ListPresentacionResponse;
import com.micrium.bd.access.jpa.modulo.productos.models.ProductoPresentacion;
import com.micrium.bd.access.jpa.modulo.productos.projection.ListPresentacionProjection;

import java.util.function.Function;

public class ProductoPresentacionMapper {

    public static final Function<ProductoPresentacionRequest, ProductoPresentacion>
        fromRequestToEntity = request ->
        ProductoPresentacion.builder()
//                .id(null)
                .productoId(request.getProductoId())
                .concepto(request.getConcepto())
                .descripcion(request.getDescripcion())
                .precioUnitario(request.getPrecioUnitario())
                .precioVenta(request.getPrecioVenta())
                .unidadMedidaId(request.getUnidadMedidaId())
                .esUnidadMinima(request.getEsUnidadMinima())
                .factorConversion(request.getFactorConversion())
                .marcaId(request.getMarcaId())
                .nombre(request.getNombre())
                .cantidadDisponibleStock(request.getCantidadDisponibleStock())
                .cantidadMinimoStock(request.getCantidadMinimoStock())
                .diasAntesExpiracion(request.getDiasAntesExpiracion())
                .esActivo(Boolean.TRUE)
                .build()
     ;

    public static final Function<ProductoPresentacion, CreateProductoPresentacionResponse>
            toResponse = entity -> {
        CreateProductoPresentacionResponse response = new CreateProductoPresentacionResponse();
        response.setId(entity.getId());
        return response;
    };

    public static final Function<ListPresentacionProjection, ListPresentacionResponse>
            fromProjectionToListPresentacionResponse = entity -> {
        ListPresentacionResponse response = new ListPresentacionResponse();
        response.setProductoId(entity.getProductoId());
        response.setProducto(entity.getProducto());
        response.setId(entity.getId());
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
        // no es buena practica poner logica del negocio en Mapper BAD
        if(entity.getCantidadDisponibleStock() <= 0) {
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
        response.setProductoId(entity.getProductoId());
        response.setConcepto(entity.getConcepto());
        response.setDescripcion(entity.getDescripcion());
        response.setPrecioUnitario(entity.getPrecioUnitario());
        response.setPrecioVenta(entity.getPrecioVenta());
        response.setUnidadMedidaId(entity.getUnidadMedidaId());
        response.setEsUnidadMinima(entity.getEsUnidadMinima());
        response.setFactorConversion(entity.getFactorConversion());
        response.setMarcaId(entity.getMarcaId());
        response.setNombre(entity.getNombre());
        response.setCantidadDisponibleStock(entity.getCantidadDisponibleStock());
        response.setCantidadMinimoStock(entity.getCantidadMinimoStock());
        response.setDiasAntesExpiracion(entity.getDiasAntesExpiracion());
        return response;
    };
}
