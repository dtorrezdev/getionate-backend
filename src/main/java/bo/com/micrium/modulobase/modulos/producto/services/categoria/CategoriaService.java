package bo.com.micrium.modulobase.modulos.producto.services.categoria;

import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.common.providers.CurrentUserProvider;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.categoria.CategoriaRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.categoria.CategoriaResponse;

import bo.com.micrium.modulobase.modulos.producto.mappers.CategoriaMapper;
import com.micrium.bd.access.jpa.modulo.productos.models.Categoria;
import com.micrium.bd.access.jpa.modulo.productos.repository.ICategoriaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CategoriaService implements ICategoriaService {

    private final ICategoriaRepository repository;

    private final CurrentUserProvider currentUserProvider;

    @Override
    public Page<CategoriaResponse> list(CategoriaRequest request, Pageable pageable) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        return repository.filter(
                queryfilterTexto(request.getNombre()),
                filterTextoQueryUpperLike(request.getNombre()),
                queryfilterTexto(request.getDescripcion()),
                filterTextoQueryUpperLike(request.getDescripcion()),
                pageable, tenantId).map(CategoriaMapper.toResponse);
    }

    @Override
    public CategoriaResponse create(CategoriaRequest request) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        final Categoria newCategoria = CategoriaMapper
                .toEntity.apply(request);
        newCategoria.setTenantId(tenantId);

        return CategoriaMapper.toResponse
                .apply(repository.save(newCategoria));
    }

    @Override
    public CategoriaResponse update(CategoriaRequest request, Long id) {
        return repository.findById(id)
                .map(categoria -> {
                    categoria.setNombre(request.getNombre());
                    categoria.setDescripcion(request.getDescripcion());
                    return categoria;
                })
                .map(repository::save)
                .map(CategoriaMapper.toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Categoria", "id", id));
    }

    @Override
    public void delete(String id) {
        final Categoria categoria = repository.findById(Long.valueOf(id))
                .orElseThrow(() ->
                        new EntityNotFoundException("Categoria", "id", id));
        categoria.setEsActivo(Boolean.FALSE);
        repository.save(categoria);
    }

    private boolean isBlanck(String dato) {
        return dato == null || dato.trim().isEmpty();
    }

    private int queryfilterTexto(String texto) {
        return this.isBlanck(texto) ? -1 : 0;
    }

    private String filterTextoQueryUpperLike(String texto) {
        return this.isBlanck(texto) ? "" : "%" + texto.trim().toUpperCase() + "%";
    }

    public CategoriaService(
            ICategoriaRepository repository,
            CurrentUserProvider currentUserProvider) {
        this.repository = repository;
        this.currentUserProvider = currentUserProvider;
    }
}
