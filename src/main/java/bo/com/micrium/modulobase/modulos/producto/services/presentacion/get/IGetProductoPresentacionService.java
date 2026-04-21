package bo.com.micrium.modulobase.modulos.producto.services.presentacion.get;

import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionResponse;

public interface IGetProductoPresentacionService {

    ProductoPresentacionResponse execute(Long presentacionId);
}
