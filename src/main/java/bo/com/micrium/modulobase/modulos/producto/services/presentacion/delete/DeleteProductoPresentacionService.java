package bo.com.micrium.modulobase.modulos.producto.services.presentacion.delete;

import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.DeleteProductoRequest;
import com.micrium.bd.access.jpa.modulo.productos.models.ProductoPresentacion;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteProductoPresentacionService implements IDeleteProductoPresentacionService {

    @Autowired
    private IProductoPresentacionRepository repository;

     @Override
    public void execute(DeleteProductoRequest presentacionId) {

         final ProductoPresentacion producto = repository.findById(presentacionId.getId())
                 .orElseThrow(() ->
                         new EntityNotFoundException("Presentacion", "id", presentacionId));
         producto.setEsActivo(Boolean.FALSE);
         repository.save(producto);
    }
}
