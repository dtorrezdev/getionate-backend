package bo.com.micrium.modulobase.modulos.producto.services.presentacion.list;

import bo.com.micrium.modulobase.common.providers.CurrentUserProvider;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.list.ListPresentacionRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.list.ListPresentacionResponse;
import bo.com.micrium.modulobase.modulos.producto.mappers.ProductoPresentacionMapper;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListProductoPresentacionServiceImpl implements IListProductoPresentacionService {

    private final IProductoPresentacionRepository repository;

    private final CurrentUserProvider currentUserProvider;

    private final Logger log = LogManager.getLogger(ListProductoPresentacionServiceImpl.class);

    public ListProductoPresentacionServiceImpl(
            IProductoPresentacionRepository repository,
            CurrentUserProvider currentUserProvider
    ) {
        this.repository = repository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public Page<ListPresentacionResponse> execute(
            ListPresentacionRequest request, Pageable page) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        log.info("params: " + request);
        log.info("page: " + page);

        return repository.filter(
                queryfilterTexto(request.getProductoId()),
                filterTextoQueryUpperLike(request.getProductoId()),
                queryfilterTexto(request.getProducto()),
                filterTextoQueryUpperLike(request.getProducto()),
                queryfilterTexto(request.getId()),
                filterTextoQueryUpperLike(request.getId()),
                queryfilterTexto(request.getPresentacion()),
                filterTextoQueryUpperLike(request.getPresentacion()),
                queryfilterTexto(request.getDescripcion()),
                filterTextoQueryUpperLike(request.getDescripcion()),
                queryfilterTexto(request.getPrincipioActivo()),
                filterTextoQueryUpperLike(request.getPrincipioActivo()),
                queryfilterTexto(request.getUnidadMedida()),
                filterTextoQueryUpperLike(request.getUnidadMedida()),
                queryfilterTexto(request.getMarca()),
                filterTextoQueryUpperLike(request.getMarca()),
                queryfilterTexto(request.getCategoria()),
                filterTextoQueryUpperLike(request.getCategoria()),
                queryfilterTexto(request.getSeControlaStock()),
                        filterTextoQuery(request.getSeControlaStock()),
                page, tenantId)
                .map(ProductoPresentacionMapper.fromProjectionToListPresentacionResponse);
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

    private String filterTextoQuery(String texto) {
        return this.isBlanck(texto) ? "" : texto.trim().toUpperCase();
    }
}
