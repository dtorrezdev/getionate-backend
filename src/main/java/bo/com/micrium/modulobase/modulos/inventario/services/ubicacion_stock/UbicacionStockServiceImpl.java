package bo.com.micrium.modulobase.modulos.inventario.services.ubicacion_stock;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.ubicacion_stock.*;
import bo.com.micrium.modulobase.modulos.inventario.mapper.UbicacionStockMapper;
import bo.com.micrium.modulobase.modulos.producto.mappers.MarcaMapper;
import com.micrium.bd.access.jpa.modulo.inventario.models.UbicacionStock;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IUbicacionStockRepository;
import com.micrium.bd.access.jpa.modulo.productos.models.ProductoPresentacion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UbicacionStockServiceImpl implements IUbicacionStockService {

    @Autowired
    private IUbicacionStockRepository repository;

    private final Logger log = LogManager.getLogger(UbicacionStockServiceImpl.class);

    @Override
    public Page<UbicacionStockResponse> list(UbicacionStockRequest request, Pageable page) {
        log.info("params: " + request);
        log.info("page: " + page);

        return repository.filter(
                queryfilterTexto(request.getSeccion()),
                filterTextoQueryUpperLike(request.getSeccion()),
                queryfilterTexto(request.getEstante()),
                filterTextoQueryUpperLike(request.getEstante()),
                queryfilterTexto(request.getNivel()),
                filterTextoQueryUpperLike(request.getNivel()),
                page)
                .map(UbicacionStockMapper.fromEntityToResponse);
    }

    @Override
    public UbicacionStockResponse create(UbicacionStockRequest request) {
        return UbicacionStockMapper.toEntity
                .andThen(repository::save)
                .andThen(UbicacionStockMapper.fromEntityToResponse)
                .apply(request);
    }

    @Override
    public UbicacionStockResponse update(UbicacionStockRequest request, Long id) {
        return repository.findById(id)
                .map(ubicacionStock -> {
                    ubicacionStock.setSeccion(request.getSeccion());
                    ubicacionStock.setEstante(request.getEstante());
                    ubicacionStock.setNivel(request.getNivel());
                    return ubicacionStock;
                })
                .map(repository::save)
                .map(UbicacionStockMapper.fromEntityToResponse)
                .orElseThrow(() -> new RuntimeException("Ubicacion Id no existe."));
    }

    @Override
    public void delete(Long id) {
        final UbicacionStock ubicacionStock = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Ubicacion Stock Id no existe."));
        ubicacionStock.setEsActivo(false);
        repository.save(ubicacionStock);

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
