package bo.com.micrium.modulobase.modulos.producto.services;

import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.marca.MarcaRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.marca.MarcaResponse;
import bo.com.micrium.modulobase.modulos.producto.mappers.MarcaMapper;
import com.micrium.bd.access.jpa.modulo.productos.repository.IMarcaRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IMarcaServiceImpl implements IMarcaService {

    @Autowired
    private IMarcaRepository repository;

//    @Autowired
//    private MarcaValidator validator;

    // private final Logger log = LogManager.getLogger(IMarcaServiceImpl.class);


    @Override
    public Page<MarcaResponse> list(Map<String, String> params, Pageable pageRequest) {

        //validator.page(params);
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
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
    }

    @Override
    public void delete(String id) {
        //id = limpiarCaracterEspecialEncriptacion(id);
        //Long idDesencriptado = Long.valueOf(id); // ConfigEncriptacion.desencryptIdToConvertLong(id);
        repository.findById(Long.valueOf(id))
            .ifPresentOrElse(
                repository::delete,
                () -> {
                    throw new RuntimeException("Marca no encontrada");
                }
            );
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
