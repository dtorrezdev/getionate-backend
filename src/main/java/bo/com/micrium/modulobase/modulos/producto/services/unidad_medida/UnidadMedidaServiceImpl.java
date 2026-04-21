package bo.com.micrium.modulobase.modulos.producto.services.unidad_medida;

import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.unidad_medida.UnidadMedidaRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.unidad_medida.UnidadMedidaResponse;
import bo.com.micrium.modulobase.modulos.producto.mappers.UnidadMedidaMapper;
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
    public Page<UnidadMedidaResponse> list(Map<String, String> params, Pageable pageRequest) {
        final String abreviatura = params.get("abreviatura");
        final String nombre = params.get("nombre");
        final String esUnidadMinima = params.get("esUnidadMinima");
        log.info(" metodo list " + params);
        final Page<UnidadMedidaResponse> map = repository.filter(
                queryfilterTexto(abreviatura),
                filterTextoQueryUpperLike(abreviatura),
                queryfilterTexto(nombre),
                filterTextoQueryUpperLike(nombre),
                queryfilterTexto(esUnidadMinima),
                filterTextoQueryUpperLike(esUnidadMinima),
                pageRequest
        ).map(UnidadMedidaMapper.fromEntityToResponse);
        log.info("data from repository: " + map);
        return map;
    }

    @Override
    public UnidadMedidaResponse create(UnidadMedidaRequest marcaRequest) {
        return null;
    }

    @Override
    public UnidadMedidaResponse update(UnidadMedidaRequest marcaRequest, String id) {
        return null;
    }

    @Override
    public void delete(String id) {

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
