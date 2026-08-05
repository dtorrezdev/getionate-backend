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

import java.util.Objects;

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
        log.info("ProductoId " +queryfilterTexto(request.getProductoId()));
        log.info("ProductoId " +filterTextoQueryUpperLike(request.getProductoId()));

        log.info("getProducto " +queryfilterTexto(request.getProducto()));
        log.info("getProducto " +filterTextoQueryUpperLike(request.getProducto()));


        log.info("getPresentacion " +queryfilterTexto(request.getPresentacion()));
        log.info("getPresentacion " +filterTextoQueryUpperLike(request.getPresentacion()));

        log.info("getDescripcion " +queryfilterTexto(request.getDescripcion()));
        log.info("getDescripcion " +filterTextoQueryUpperLike(request.getDescripcion()));

        log.info("getPrincipioActivo " +queryfilterTexto(request.getPrincipioActivo()));
        log.info("getPrincipioActivo " +filterTextoQueryUpperLike(request.getPrincipioActivo()));

        log.info("getUnidadMedida " +queryfilterTexto(request.getUnidadMedida()));
        log.info("getUnidadMedida " +filterTextoQueryUpperLike(request.getUnidadMedida()));

        log.info("getMarca " +queryfilterTexto(request.getMarca()));
        log.info("getMarca " +filterTextoQueryUpperLike(request.getMarca()));


        log.info("getCategoria " +queryfilterTexto(request.getCategoria()));
        log.info("getCategoria " +filterTextoQueryUpperLike(request.getCategoria()));

        if(Objects.nonNull(request.getSeControlaStock())) {
            return repository.getProductoStockable(
                            queryfilterTexto(request.getCodigo()),
                            filterTextoQueryUpperLike(request.getCodigo()),
                            queryfilterTexto(request.getProducto()),
                            filterTextoQueryUpperLike(request.getProducto()),
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
                            page, tenantId)
                    .map(ProductoPresentacionMapper.fromProjectionToListPresentacionResponse);
        }

        return repository.filter(
                queryfilterTexto(request.getCodigo()),
                filterTextoQueryUpperLike(request.getCodigo()),
                queryfilterTexto(request.getProducto()),
                filterTextoQueryUpperLike(request.getProducto()),
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
