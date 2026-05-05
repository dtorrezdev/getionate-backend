package bo.com.micrium.modulobase.modulos.producto.services.marca;

import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.marca.MarcaRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.marca.MarcaResponse;
import bo.com.micrium.modulobase.modulos.producto.mappers.MarcaMapper;
import com.micrium.bd.access.jpa.modulo.productos.models.Marca;
import com.micrium.bd.access.jpa.modulo.productos.models.ProductoPresentacion;
import com.micrium.bd.access.jpa.modulo.productos.repository.IMarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MarcaServiceImpl implements IMarcaService {

    private final IMarcaRepository repository;
    // private final Logger log = LogManager.getLogger(IMarcaServiceImpl.class);

    @Override
    public Page<MarcaResponse> list(Map<String, String> params, Pageable pageRequest) {

        final String nombre = params.get("nombre");
        final String descripcion = params.get("descripcion");

        return repository.filter(
                queryfilterTexto(nombre),
                filterTextoQueryUpperLike(nombre),
                queryfilterTexto(descripcion),
                filterTextoQueryUpperLike(descripcion),
                pageRequest).map(MarcaMapper.toResponse);
    }

    @Override
    public MarcaResponse create(MarcaRequest request) {

        return MarcaMapper.toEntity
                .andThen(repository::save)
                .andThen(MarcaMapper.toResponse)
                .apply(request);
    }

    @Override
    public MarcaResponse update(MarcaRequest request, String id) {
        //id = limpiarCaracterEspecialEncriptacion(id);
        //Long idDesencriptado = Long.valueOf(id); //ConfigEncriptacion.desencryptIdToConvertLong(id);
        return repository.findById(Long.valueOf(id))
                .map(marcaUpdated -> {
                    marcaUpdated.setNombre(request.getNombre());
                    marcaUpdated.setDescripcion(request.getDescripcion());
                    return marcaUpdated;
                })
                .map(repository::save)
                .map(MarcaMapper.toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Marca", "id", id));
    }

    @Override
    public void delete(String id) {
        //id = limpiarCaracterEspecialEncriptacion(id);
        //Long idDesencriptado = Long.valueOf(id); // ConfigEncriptacion.desencryptIdToConvertLong(id);
        final Marca marca = repository.findById(Long.valueOf(id))
                .orElseThrow(() ->
                        new EntityNotFoundException("Marca", "id", id));
        marca.setEsActivo(Boolean.FALSE);
        repository.save(marca);
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

    public MarcaServiceImpl(IMarcaRepository repository) {
        this.repository = repository;
    }
}
