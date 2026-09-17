package com.dtorrez.main.modulos.producto.services.categoria;

import com.dtorrez.main.modulos.producto.controllers.dtos.categoria.CategoriaRequest;
import com.dtorrez.main.modulos.producto.controllers.dtos.categoria.CategoriaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ICategoriaService {

    Page<CategoriaResponse> list(CategoriaRequest params, Pageable pageable);
    CategoriaResponse create(CategoriaRequest request);
    CategoriaResponse update(CategoriaRequest request, Long id);
    void delete(String id);
}
