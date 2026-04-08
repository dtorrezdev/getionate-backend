package bo.com.micrium.modulobase.modulos.producto.mappers;

import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.CreateProductoPresentacionResponse;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionRequest;
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
        response.setCategoriaId(entity.getCategoriaId());
        response.setCategoria(entity.getCategoria());
        response.setCantidadMinimoStock(entity.getCantidadMinimoStock());
        response.setCantidadDisponibleStock(entity.getCantidadDisponibleStock());
        return response;
    };
}
