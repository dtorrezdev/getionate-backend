package bo.com.micrium.modulobase.modulos.producto.services.unidad_medida;

import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.unidad_medida.UnidadMedidaRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.unidad_medida.UnidadMedidaResponse;
import bo.com.micrium.modulobase.modulos.producto.mappers.MarcaMapper;
import bo.com.micrium.modulobase.modulos.producto.mappers.UnidadMedidaMapper;
import com.micrium.bd.access.jpa.modulo.productos.models.ProductoPresentacion;
import com.micrium.bd.access.jpa.modulo.productos.models.UnidadMedida;
import com.micrium.bd.access.jpa.modulo.productos.repository.IUnidadMedidaRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UnidadMedidaServiceImpl implements  IUnidadMedidaService {

    @Autowired
    private IUnidadMedidaRepository repository;

    private final Logger log = LogManager.getLogger(UnidadMedidaServiceImpl.class);

    @Override
    public Page<UnidadMedidaResponse> list(UnidadMedidaRequest request, Pageable pageRequest) {
        log.info(" list(): request " + request);
        return repository.filter(
                queryfilterTexto(request.getAbreviatura()),
                filterTextoQueryUpperLike(request.getAbreviatura()),
                queryfilterTexto(request.getNombre()),
                filterTextoQueryUpperLike(request.getNombre()),
                queryfilterTexto(request.getEsUnidadMinima()),
                filterTextoQueryUpperLike(request.getEsUnidadMinima()),
                pageRequest
        ).map(UnidadMedidaMapper.fromEntityToResponse);
    }

    @Override
    public UnidadMedidaResponse create(UnidadMedidaRequest request) {
        return UnidadMedidaMapper.fromRequestToEntity
                .andThen(repository::save)
                .andThen(UnidadMedidaMapper.fromEntityToResponse)
                .apply(request);
    }

    @Override
    public UnidadMedidaResponse update(UnidadMedidaRequest request, Long id) {
        return repository.findById(id)
                .map(unidadMedida -> {
                    unidadMedida.setAbreviatura(request.getAbreviatura());
                    unidadMedida.setNombre(request.getNombre());
                    unidadMedida.setEsUnidadMinima(Boolean.valueOf(request.getEsUnidadMinima()));
                    return unidadMedida;
                })
                .map(repository::save)
                .map(UnidadMedidaMapper.fromEntityToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Unidad Medida", "id", id));
    }

    @Override
    public void delete(Long id) {
        final UnidadMedida unidadMedida = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Unidad Medida", "id", id));
        unidadMedida.setEsActivo(false);
        repository.save(unidadMedida);
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
