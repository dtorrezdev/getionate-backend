package bo.com.micrium.modulobase.modulos.ventas.services.venta.list;

import bo.com.micrium.modulobase.common.providers.CurrentUserProvider;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.list.ListVentaRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.list.ListVentaResponse;
import bo.com.micrium.modulobase.modulos.ventas.mapper.VentaMapper;
import com.micrium.bd.access.jpa.modulo.venta.repository.IVentaRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListVentaServiceImpl implements IListVentaService {

    @Autowired
    private IVentaRepository repository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

     private final Logger log = LogManager.getLogger(ListVentaServiceImpl.class);

    @Override
    public Page<ListVentaResponse> execute(ListVentaRequest request, Pageable page) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        log.info("params: " + request);
        log.info("page: " + page);
        return repository.filter(
                queryfilterTexto(request.getCodigo()),
                filterTextoQueryUpperLike(request.getCodigo()),
                queryfilterTexto(request.getGlosa()),
                filterTextoQueryUpperLike(request.getGlosa()),
                queryfilterTexto(request.getCliente()),
                filterTextoQueryUpperLike(request.getCliente()),
                queryfilterTexto(request.getEstado()),
                filterTextoQueryUpperLike(request.getEstado()),
                queryfilterTexto(request.getFechaInicio(), request.getFechaFin()),
                filterTextoQuery(request.getFechaInicio()),
                filterTextoQuery(request.getFechaFin()),
                page, tenantId)
                .map(VentaMapper.fromProjectionToListVentaResponse);
    }

    private boolean isBlanck(String dato) {
        return dato == null || dato.trim().isEmpty();
    }

    private int queryfilterTexto(String texto) {
        return this.isBlanck(texto) ? -1 : 0;
    }

    private int queryfilterTexto(String texto, String texto2) {
        return this.isBlanck(texto) &&  this.isBlanck(texto2) ? -1 : 0;
    }

    private String filterTextoQueryUpperLike(String texto) {
        return this.isBlanck(texto) ? "" : "%" + texto.trim().toUpperCase() + "%";
    }

    private String filterTextoQuery(String texto) {
        return this.isBlanck(texto) ? "" : texto.trim().toUpperCase();
    }
}
