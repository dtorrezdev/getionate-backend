package bo.com.micrium.modulobase.modulos.producto.services.producto;

import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.common.providers.CurrentUserProvider;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto.ProductoRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto.ProductoResponse;
import bo.com.micrium.modulobase.modulos.producto.mappers.ProductoMapper;
import com.micrium.bd.access.jpa.modulo.productos.models.Producto;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductoServiceImpl implements IProductoService {

    private final IProductoRepository repository;
    private final CurrentUserProvider currentUserProvider;

    @Override
    public Page<ProductoResponse> list(Map<String, String> params, Pageable pageRequest) {

        final String codigo = params.get("codigo");
        final String nombre = params.get("nombre");
        final String descripcion = params.get("descripcion");
        final Long tenantId = currentUserProvider.getUserTenantId();

        return repository.filter(
                queryfilterTexto(codigo),
                filterTextoQueryUpperLike(codigo),
                queryfilterTexto(nombre),
                filterTextoQueryUpperLike(nombre),
                queryfilterTexto(descripcion),
                filterTextoQueryUpperLike(descripcion),
                pageRequest, tenantId).map(ProductoMapper.fromProjectiontoResponse);
    }

    @Override
    public ProductoResponse get(Long id) {
        return repository.findById(id)
                .map(ProductoMapper.toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Producto", "id", id));
    }

    @Override
    public ProductoResponse create(ProductoRequest request) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        final Producto producto = ProductoMapper.toEntity.apply(request);
        producto.setTenantId(tenantId);

        return ProductoMapper.toResponse
                .apply(repository.save(producto));
    }

    @Override
    public ProductoResponse update(ProductoRequest request, Long id) {
        return repository.findById(id)
                .map(productoUpdated -> {
                    productoUpdated.setNombre(request.getNombre());
                    productoUpdated.setDescripcion(request.getDescripcion());
                    productoUpdated.setCategoriaId(request.getCategoriaId());
                    return productoUpdated;
                })
                .map(repository::save)
                .map(ProductoMapper.toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Producto", "id", id));
    }

    @Override
    public void delete(Long id) {
        final Producto producto = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Producto", "id", id));
        producto.setEsActivo(Boolean.FALSE);
        repository.save(producto);
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

    public ProductoServiceImpl(
            IProductoRepository repository,
            CurrentUserProvider currentUserProvider) {
        this.repository = repository;
        this.currentUserProvider = currentUserProvider;
    }
}

