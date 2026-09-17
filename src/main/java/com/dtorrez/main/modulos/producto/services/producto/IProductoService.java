package com.dtorrez.main.modulos.producto.services.producto;

import com.dtorrez.main.modulos.producto.controllers.dtos.producto.ProductoListRequest;
import com.dtorrez.main.modulos.producto.controllers.dtos.producto.ProductoRequest;
import com.dtorrez.main.modulos.producto.controllers.dtos.producto.ProductoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductoService {

    Page<ProductoResponse> list(ProductoListRequest params, Pageable pageRequest);

    ProductoResponse get(Long id);

    ProductoResponse create(ProductoRequest productoRequest);

    ProductoResponse update(ProductoRequest productoRequest, Long id);

    void delete(Long id);
}

