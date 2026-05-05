package bo.com.micrium.modulobase.modulos.producto.services.presentacion.get;

import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionResponse;
import bo.com.micrium.modulobase.modulos.producto.mappers.ProductoPresentacionMapper;
import com.micrium.bd.access.jpa.modulo.productos.models.ProductoPresentacion;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetProductoPresentacionService implements IGetProductoPresentacionService {

    @Autowired
    private IProductoPresentacionRepository repository;

    @Override
    public ProductoPresentacionResponse execute(Long presentacionId) {

        final ProductoPresentacion presentacion = this.repository.findById(presentacionId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Presentacion", "id", presentacionId));

        return ProductoPresentacionMapper.toGetResponse.apply(presentacion);
    }
}
