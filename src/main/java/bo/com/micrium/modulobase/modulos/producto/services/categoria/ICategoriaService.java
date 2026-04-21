package bo.com.micrium.modulobase.modulos.producto.services.categoria;

import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.categoria.CategoriaRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.categoria.CategoriaResponse;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.marca.MarcaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


public interface ICategoriaService {

    Page<CategoriaResponse> list(CategoriaRequest params, Pageable pageable);
    CategoriaResponse create(CategoriaRequest request);
    CategoriaResponse update(CategoriaRequest request, Long id);
    void delete(String id);
}
