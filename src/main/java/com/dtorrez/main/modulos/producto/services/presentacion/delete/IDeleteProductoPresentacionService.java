package com.dtorrez.main.modulos.producto.services.presentacion.delete;

import com.dtorrez.main.modulos.producto.controllers.dtos.producto_presentacion.DeleteProductoRequest;

public interface IDeleteProductoPresentacionService {

    void execute(DeleteProductoRequest request);

}
