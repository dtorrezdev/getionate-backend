package bo.com.micrium.modulobase.modulos.compra.services.recepcion.create;

import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.modulos.compra.Mappers.RecepcionProductoMapper;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.crear.DetalleRecepcionRequest;
import com.micrium.bd.access.jpa.modulo.compra.models.RecepcionProducto;
import com.micrium.bd.access.jpa.modulo.compra.repositories.ICompraRepository;
import com.micrium.bd.access.jpa.modulo.compra.repositories.IRecepcionProductoRepository;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.crear.RecepcionProductoRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.crear.RecepcionProductoResponse;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateRecepcionProductoServiceImpl implements ICreateRecepcionProductoService {

    @Autowired
    private IRecepcionProductoRepository repository;

    @Autowired
    private ICompraRepository compraRepository;

    @Autowired
    private IProductoPresentacionRepository productoRepository;

    private final Logger log = LogManager.getLogger(CreateRecepcionProductoServiceImpl.class);

    @Override
    @Transactional
    public RecepcionProductoResponse execute(RecepcionProductoRequest request) {

        // 1. Validar request
        this.validarRequest(request);
        log.info("request valido");

        // 2. Mapear request a entity
        final RecepcionProducto newRecepcion = RecepcionProductoMapper.toEntity.apply(request);
        log.info("request mapeado a entity");
        // crear movimientos

        //newRecepcion.setMovimientoId(null);

        // 3. Guardar en repositorio
        return RecepcionProductoMapper.toResponse
                .apply(repository.save(newRecepcion));
    }

    private void validarRequest(RecepcionProductoRequest request) {
        // Validar que el proveedor existe
        final var compraId = request.getCompraId();
        compraRepository.findById(compraId)
                .orElseThrow(() -> new EntityNotFoundException("Compra", "id", compraId));
        // Validar todos los detalles
        request.getDetalle().forEach(this::validarDetalle);
    }

    private void validarDetalle(DetalleRecepcionRequest detalle) {
        // Validar que la presentación existe
        productoRepository.findById(detalle.getPresentacionId())
                .orElseThrow(() -> new EntityNotFoundException("Presentacion", "id", detalle.getPresentacionId()));
    }
}

