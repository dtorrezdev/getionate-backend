package bo.com.micrium.modulobase.modulos.compra.services.proveedor;

import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.common.providers.CurrentUserProvider;
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
    private final CurrentUserProvider currentUserProvider;

    @Override
    public Page<ProveedorResponse> list(ProveedorRequest request, Pageable pageRequest) {
        final Long tenantId = currentUserProvider.getUserTenantId();

        return repository.filter(
                queryfilterTexto(request.getNombre()),
                filterTextoQueryUpperLike(request.getNombre()),
                queryfilterTexto(request.getDescripcion()),
                filterTextoQueryUpperLike(request.getDescripcion()),
                pageRequest, tenantId).map(ProveedorMapper.toResponse);
    }

    @Override
    public ProveedorResponse create(ProveedorRequest request) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        final var proveedor = ProveedorMapper.toEntity.apply(request);
        proveedor.setTenantId(tenantId);

        return ProveedorMapper.toResponse
                .apply(repository.save(proveedor));
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
                .orElseThrow(() -> new EntityNotFoundException("Proveedor","id", id));
    }

    @Override
    public void delete(String id) {
        final Proveedor proveedor = repository.findById(Long.valueOf(id))
                .orElseThrow(() ->
                        new EntityNotFoundException("Proveedor","id", id));
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

    public ProveedorService(
            IProveedorRepository repository,
            CurrentUserProvider currentUserProvider) {
        this.repository = repository;
        this.currentUserProvider = currentUserProvider;
    }
}
