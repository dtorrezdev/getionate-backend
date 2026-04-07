package bo.com.micrium.modulobase.modulos.producto.services.presentacion.create;

import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.CreateProductoPresentacionResponse;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionResponse;

public interface ICreateProductoPresentacionService {

    CreateProductoPresentacionResponse execute(ProductoPresentacionRequest request);

}
