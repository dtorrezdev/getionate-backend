package com.dtorrez.main.modulos.producto.services.presentacion.update;

import com.dtorrez.main.common.enums.EnumEvento;
import com.dtorrez.main.common.exceptions.EntityNotFoundException;
import com.dtorrez.main.common.providers.CurrentUserProvider;
import com.dtorrez.main.modulos.evento.services.EventoNotificaconServiceImpl;
import com.dtorrez.main.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionRequest;
import com.dtorrez.main.modulos.producto.controllers.dtos.producto_presentacion.ProductoPresentacionResponse;
import com.dtorrez.main.modulos.producto.mappers.ProductoPresentacionMapper;
import com.micrium.bd.access.jpa.modulo.productos.repository.IMarcaRepository;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoRepository;
import com.micrium.bd.access.jpa.modulo.productos.repository.IUnidadMedidaRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateProductoPresentacionService implements IUpdateProductoPresentacionService {

    @Autowired
    private IProductoPresentacionRepository repository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

    private final Logger log = LogManager.getLogger(UpdateProductoPresentacionService.class);

    @Override
    public ProductoPresentacionResponse execute(ProductoPresentacionRequest request, Long id) {

        log.info("execute update service PP");
        this.validateRequest(request);
        final Long tenantId = currentUserProvider.getUserTenantId();
        final ProductoPresentacionResponse updateProducto = repository.findById(id)
                .map(producto -> {
                    producto.setId(id);
                    producto.setProductoId(request.getProductoId());
                    producto.setCodigo(request.getCodigo());
                    producto.setImagen(request.getImagen());
                    producto.setNombre(request.getNombre());
                    producto.setConcepto(request.getConcepto());
                    producto.setDescripcion(request.getDescripcion());
                    producto.setUnidadMedidaId(request.getUnidadMedidaId());
                    producto.setCantidadDisponibleStock(request.getCantidadDisponibleStock());
                    producto.setPrecioUnitario(request.getPrecioUnitario());
                    producto.setPrecioVenta(request.getPrecioVenta());
                    producto.setMarcaId(request.getMarcaId());
                    producto.setDiasAntesExpiracion(request.getDiasAntesExpiracion());
                    producto.setCantidadMinimoStock(request.getCantidadMinimoStock());
                    producto.setSeControlaStock(request.getSeControlaStock());
                    producto.setEsActivo(Boolean.TRUE);
                    producto.setTenantId(tenantId);
                    return producto;
                })
                .map(repository::save)
                .map(ProductoPresentacionMapper.toResponse)
                .orElseThrow(() ->
                        new EntityNotFoundException("Presentacion", "id" ,id));

        log.info("print updated: " + updateProducto);

        if(request.getSeControlaStock()) {
            this.registrarEventoProducto(updateProducto);
        }

        return updateProducto;
    }

    @Autowired
    private EventoNotificaconServiceImpl eventoNotificacionService;
    // Refactorizar
    private void registrarEventoProducto(ProductoPresentacionResponse producto) {

        if(producto.getDiasAntesExpiracion() == null || producto.getDiasAntesExpiracion() <= 1) {
            eventoNotificacionService.registrarEventoYNotificacionProducto(
                    EnumEvento.Type.PROD_SIN_DIAS_ANTES_EXPIRACION.name(),
                    producto.getId());
        }

        if(producto.getCantidadMinimoStock() == null || producto.getCantidadMinimoStock() <= 1) {
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
                .orElseThrow(() -> new EntityNotFoundException("Producto", "id", request.getProductoId()));

        unidadMedidaRepository.findById(request.getUnidadMedidaId())
                .orElseThrow(()-> new EntityNotFoundException("Unidad Medida", "id", request.getUnidadMedidaId()));

        marcaRepository.findById(request.getMarcaId())
                .orElseThrow(()-> new EntityNotFoundException("Marca", "id", request.getMarcaId()));

//        repository.findByNombreAndProductoIdAndMarcaId(
//                        request.getNombre().toUpperCase(),
//                        request.getProductoId(),
//                        request.getMarcaId())
//                .ifPresent((ele)-> {
//                    throw new DuplicateEntityException("Presentacion",
//                            "nombre,productoId,marcaId,", String.format("%s,%s,%s", request.getNombre(), request.getProductoId(), request.getMarcaId()));
//                });
    }
}
