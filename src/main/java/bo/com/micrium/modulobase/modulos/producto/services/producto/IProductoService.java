package bo.com.micrium.modulobase.modulos.producto.services.producto;

import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto.ProductoListRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto.ProductoRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto.ProductoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface IProductoService {

    Page<ProductoResponse> list(ProductoListRequest params, Pageable pageRequest);

    ProductoResponse get(Long id);

    ProductoResponse create(ProductoRequest productoRequest);

    ProductoResponse update(ProductoRequest productoRequest, Long id);

    void delete(Long id);
}

