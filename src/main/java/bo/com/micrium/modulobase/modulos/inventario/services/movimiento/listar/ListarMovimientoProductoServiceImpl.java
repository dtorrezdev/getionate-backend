package bo.com.micrium.modulobase.modulos.inventario.services.movimiento.listar;

import bo.com.micrium.modulobase.common.providers.CurrentUserProvider;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.list.ListMovimientoProductoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.list.ListMovimientoProductoResponse;
import bo.com.micrium.modulobase.modulos.producto.mappers.MovimientoProductoMapper;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IMovimientoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarMovimientoProductoServiceImpl implements IListarMovimientoProductoService {

    @Autowired
    private IMovimientoRepository repository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

    private final Logger log = LogManager.getLogger(ListarMovimientoProductoServiceImpl.class);

    @Override
    public Page<ListMovimientoProductoResponse> listar(ListMovimientoProductoRequest request, Pageable page) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        log.info("params: " + request);
        log.info("page: " + page);

        return repository.filter(
                queryfilterTexto(request.getMotivo()),
                filterTextoQueryUpperLike(request.getMotivo()),
                queryfilterTexto(request.getTipoMovimiento()),
                filterTextoQueryUpperLike(request.getTipoMovimiento()),
                queryfilterTexto(request.getProducto()),
                filterTextoQueryUpperLike(request.getProducto()),
                queryfilterTexto(request.getLote()),
                filterTextoQueryUpperLike(request.getLote()),
                queryfilterTexto(request.getUnidadMedida()),
                filterTextoQueryUpperLike(request.getUnidadMedida()),
                queryfilterTexto(request.getUnidadMedidaShort()),
                filterTextoQueryUpperLike(request.getUnidadMedidaShort()),
                page, tenantId).map(MovimientoProductoMapper.fromProjectionToResponse);
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
}
