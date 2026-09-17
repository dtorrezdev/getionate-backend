package com.dtorrez.main.modulos.producto.services.presentacion.get;

import com.dtorrez.main.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionResponse;

public interface IGetProductoPresentacionService {

    ProductoPresentacionResponse execute(Long presentacionId);
}
