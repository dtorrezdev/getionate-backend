package bo.com.micrium.modulobase.modulos.producto.mappers;

import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.CreateProductoPresentacionResponse;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.list.ListPresentacionResponse;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.VentaResponse;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.list.ListVentaResponse;
import com.micrium.bd.access.jpa.modulo.productos.models.ProductoPresentacion;
import com.micrium.bd.access.jpa.modulo.productos.projection.ListPresentacionProjection;
import com.micrium.bd.access.jpa.modulo.venta.models.Venta;
import com.micrium.bd.access.jpa.modulo.venta.projection.ListVentaProjection;

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
            fromProjectionToListPresentacionResponse = venta -> {
        ListPresentacionResponse response = new ListPresentacionResponse();
        response.setProductoId(venta.getProductoId());
        response.setProducto(venta.getProducto());
        response.setId(venta.getId());
        response.setPresentacion(venta.getPresentacion());
        response.setPresentacionLarga(venta.getPresentacionLarga());
        response.setPrincipioActivo(venta.getPrincipioActivo());
        response.setUnidadMedidaId(venta.getUnidadMedidaId());
        response.setUnidadMedidaShort(venta.getUnidadMedidaShort());
        response.setUnidadMedida(venta.getUnidadMedida());
        response.setMarcaId(venta.getMarcaId());
        response.setMarca(venta.getMarca());
        response.setPrecioVenta(venta.getPrecioVenta());
        response.setCategoriaId(venta.getCategoriaId());
        response.setCategoria(venta.getCategoria());
        return response;
    };
}
