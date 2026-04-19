package bo.com.micrium.modulobase.modulos.producto.services.presentacion.create;

import bo.com.micrium.modulobase.common.enums.EnumEvento;
import bo.com.micrium.modulobase.common.enums.EnumInventario;
import bo.com.micrium.modulobase.modulos.evento.services.EventoNotificaconServiceImpl;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.*;
import bo.com.micrium.modulobase.modulos.producto.mappers.ProductoPresentacionMapper;
import com.micrium.bd.access.jpa.modulo.productos.models.ProductoPresentacion;
import com.micrium.bd.access.jpa.modulo.productos.repository.IMarcaRepository;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoRepository;
import com.micrium.bd.access.jpa.modulo.productos.repository.IUnidadMedidaRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateProductoPresentacionServiceImpl implements ICreateProductoPresentacionService {

    @Autowired
    private IProductoPresentacionRepository repository;

    private final Logger log = LogManager.getLogger(CreateProductoPresentacionServiceImpl.class);

    @Override
    public CreateProductoPresentacionResponse execute(ProductoPresentacionRequest request) {
        log.info("execute create service PP");
        this.validateRequest(request);

        final ProductoPresentacion prodPresentacion = ProductoPresentacionMapper
                .fromRequestToEntity
                .apply(request);

        log.info("print: " + prodPresentacion);

        final CreateProductoPresentacionResponse createdProducto = ProductoPresentacionMapper.toResponse
                .apply(repository.save(prodPresentacion));

        this.registrarEventoProducto(request, createdProducto);
        return createdProducto;
    }

    @Autowired
    private EventoNotificaconServiceImpl eventoNotificacionService;

    private void registrarEventoProducto(ProductoPresentacionRequest request, CreateProductoPresentacionResponse producto) {

        if(request.getDiasAntesExpiracion() == null || request.getDiasAntesExpiracion() <= 1) {
            eventoNotificacionService.registrarEventoYNotificacionProducto(
                    EnumEvento.Type.PROD_SIN_DIAS_ANTES_EXPIRACION.name(),
                    producto.getId());
        }

        if(request.getCantidadMinimoStock() == null || request.getCantidadMinimoStock() <= 1) {
            eventoNotificacionService.registrarEventoYNotificacionProducto(
                    EnumEvento.Type.PROD_SIN_MIN_STOCK_DISPONIBLE.name(),
                    producto.getId());
        }
    }

    @Autowired
    private IProductoRepository productoRepository;

    @Autowired
    private IUnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    private IMarcaRepository marcaRepository;

    private void validateRequest(ProductoPresentacionRequest request) {

        productoRepository.findById(request.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto Id no existe."));

        unidadMedidaRepository.findById(request.getUnidadMedidaId())
                .orElseThrow(()-> new RuntimeException("Unidad Medida Id no existe."));

        marcaRepository.findById(request.getMarcaId())
                .orElseThrow(()-> new RuntimeException("Marca Id no existe."));

        repository.findByNombreAndProductoIdAndMarcaId(
                request.getNombre().toUpperCase(),
                request.getProductoId(),
                request.getMarcaId())
                .ifPresent((ele)-> {
                    throw new RuntimeException("Presentacion ya se encuentra registrada");
                });

    }

}
