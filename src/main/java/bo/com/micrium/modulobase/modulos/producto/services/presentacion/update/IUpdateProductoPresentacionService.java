package bo.com.micrium.modulobase.modulos.producto.services.presentacion.update;

import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionResponse;

public interface IUpdateProductoPresentacionService {
    ProductoPresentacionResponse execute(ProductoPresentacionRequest request , Long id );
}
