package bo.com.micrium.modulobase.modulos.compra.services.proveedor;

import bo.com.micrium.modulobase.modulos.compra.Mappers.ProveedorMapper;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.proveedor.ProveedorRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.proveedor.ProveedorResponse;
import com.micrium.bd.access.jpa.modulo.compra.models.Proveedor;
import com.micrium.bd.access.jpa.modulo.compra.repositories.IProveedorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProveedorService implements IProveedorService {

    private final IProveedorRepository repository;

    @Override
    public Page<ProveedorResponse> list(ProveedorRequest request, Pageable pageRequest) {
        return repository.filter(
                queryfilterTexto(request.getNombre()),
                filterTextoQueryUpperLike(request.getNombre()),
                queryfilterTexto(request.getDescripcion()),
                filterTextoQueryUpperLike(request.getDescripcion()),
                pageRequest).map(ProveedorMapper.toResponse);
    }

    @Override
    public ProveedorResponse create(ProveedorRequest request) {
        return ProveedorMapper.toEntity
                .andThen(repository::save)
                .andThen(ProveedorMapper.toResponse)
                .apply(request);
    }

    @Override
    public ProveedorResponse update(ProveedorRequest request, Long id) {
        return repository.findById(id)
                .map(proveedor -> {
                    proveedor.setNombre(request.getNombre());
                    proveedor.setDescripcion(request.getDescripcion());
                    return proveedor;
                })
                .map(repository::save)
                .map(ProveedorMapper.toResponse)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrada"));
    }

    @Override
    public void delete(String id) {
        final Proveedor proveedor = repository.findById(Long.valueOf(id))
                .orElseThrow(() ->
                        new RuntimeException("Proveedor no existe."));
        proveedor.setEsActivo(Boolean.FALSE);
        repository.save(proveedor);
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

    public ProveedorService(IProveedorRepository repository) {
        this.repository = repository;
    }
}
