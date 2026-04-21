package bo.com.micrium.modulobase.modulos.producto.services.categoria;

import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.categoria.CategoriaRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.categoria.CategoriaResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CategoriaService implements ICategoriaService {

    @Override
    public Page<CategoriaResponse> list(CategoriaRequest params, Pageable pageable) {
        return null;
    }

    @Override
    public CategoriaResponse create(CategoriaRequest request) {
        return null;
    }

    @Override
    public CategoriaResponse update(CategoriaRequest request, Long id) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
