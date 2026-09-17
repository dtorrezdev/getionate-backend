package com.dtorrez.main.modulos.producto.services.presentacion.update;

import com.dtorrez.main.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionRequest;
import com.dtorrez.main.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionResponse;

public interface IUpdateProductoPresentacionService {
    ProductoPresentacionResponse execute(ProductoPresentacionRequest request , Long id );
}
