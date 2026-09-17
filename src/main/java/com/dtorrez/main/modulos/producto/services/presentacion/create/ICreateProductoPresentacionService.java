package com.dtorrez.main.modulos.producto.services.presentacion.create;

import com.dtorrez.main.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionRequest;
import com.dtorrez.main.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionResponse;

public interface ICreateProductoPresentacionService {
    ProductoPresentacionResponse execute(ProductoPresentacionRequest request);
}
