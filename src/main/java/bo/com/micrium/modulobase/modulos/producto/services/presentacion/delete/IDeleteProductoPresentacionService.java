package bo.com.micrium.modulobase.modulos.producto.services.presentacion.delete;

import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.DeleteProductoRequest;

public interface IDeleteProductoPresentacionService {

    void execute(DeleteProductoRequest request);

}
