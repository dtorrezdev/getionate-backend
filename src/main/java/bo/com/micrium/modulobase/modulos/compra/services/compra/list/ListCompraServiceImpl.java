package bo.com.micrium.modulobase.modulos.compra.services.compra.list;

import bo.com.micrium.modulobase.common.providers.CurrentUserProvider;
import bo.com.micrium.modulobase.modulos.compra.Mappers.CompraMapper;
import bo.com.micrium.modulobase.modulos.ventas.mapper.VentaMapper;
import bo.com.micrium.modulobase.modulos.ventas.services.venta.list.ListVentaServiceImpl;
import com.micrium.bd.access.jpa.modulo.compra.repositories.ICompraRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.list.ListCompraRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.list.ListCompraResponse;

import java.util.ArrayList;

@Service
public class ListCompraServiceImpl implements IListCompraService {

    @Autowired
    private ICompraRepository repository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

    private final Logger log = LogManager.getLogger(ListCompraServiceImpl.class);

    @Override
    public Page<ListCompraResponse> execute(ListCompraRequest request, Pageable page) {
        log.info("params: " + request);
        log.info("page: " + page);
        long tenantId = currentUserProvider.getUserTenantId();
        return repository.filter(
                        queryfilterTexto(request.getId()),
                        filterTextoQueryUpperLike(request.getId()),
                        queryfilterTexto(request.getCodigo()),
                        filterTextoQueryUpperLike(request.getCodigo()),
                        queryfilterTexto(request.getGlosa()),
                        filterTextoQueryUpperLike(request.getGlosa()),
                        queryfilterTexto(request.getTotal()),
                        filterTextoQueryUpperLike(request.getTotal()),
                        queryfilterTexto(request.getFechaCompra()),
                        filterTextoQueryUpperLike(request.getFechaCompra()),
                        queryfilterTexto(request.getFechaSolicitud()),
                        filterTextoQueryUpperLike(request.getFechaSolicitud()),
                        queryfilterTexto(request.getProvedor()),
                        filterTextoQueryUpperLike(request.getProvedor()),
                        queryfilterTexto(request.getEstado()),
                        filterTextoQueryUpper(request.getEstado()),
                        queryfilterTexto(request.getTipo()),
                        filterTextoQueryUpper(request.getTipo()),
                        page, tenantId)
                .map(CompraMapper.fromProjectionToListCompraResponse);
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

    private String filterTextoQueryUpper(String texto) {
        return this.isBlanck(texto) ? "" : texto.trim().toUpperCase();
    }
}

