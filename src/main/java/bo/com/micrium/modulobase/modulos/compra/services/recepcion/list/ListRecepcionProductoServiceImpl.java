package bo.com.micrium.modulobase.modulos.compra.services.recepcion.list;

import bo.com.micrium.modulobase.modulos.compra.Mappers.RecepcionProductoMapper;
import com.micrium.bd.access.jpa.modulo.compra.repositories.IRecepcionProductoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.list.ListRecepcionProductoRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.list.ListRecepcionProductoResponse;

@Service
public class ListRecepcionProductoServiceImpl implements IListRecepcionProductoService {

    @Autowired
    private IRecepcionProductoRepository repository;

    private final Logger log = LogManager.getLogger(ListRecepcionProductoServiceImpl.class);

    @Override
    public Page<ListRecepcionProductoResponse> execute(ListRecepcionProductoRequest request, Pageable page) {
        log.info("params: " + request);
        log.info("page: " + page);
        return repository.filter(
                        queryfilterTexto(request.getId()),
                        filterTextoQueryUpperLike(request.getId()),
                        queryfilterTexto(request.getCodigo()),
                        filterTextoQueryUpperLike(request.getCodigo()),
                        queryfilterTexto(request.getGlosa()),
                        filterTextoQueryUpperLike(request.getGlosa()),
                        queryfilterTexto(request.getTotal()),
                        filterTextoQueryUpperLike(request.getTotal()),
                        queryfilterTexto(request.getFechaRegistro()),
                        filterTextoQueryUpperLike(request.getFechaRegistro()),
                        queryfilterTexto(request.getProveedor()),
                        filterTextoQueryUpperLike(request.getProveedor()),
                        page)
                .map(RecepcionProductoMapper.fromProjectionToListRecepcionResponse);
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
