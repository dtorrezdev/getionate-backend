package com.dtorrez.main.modulos.inventario.services.movimiento.registrar;

import com.dtorrez.main.common.exceptions.EntityNotFoundException;
import com.dtorrez.main.common.providers.CurrentUserProvider;
import com.dtorrez.main.modulos.compra.Mappers.MovimientoInventarioRecepcionMapper;
import com.dtorrez.main.modulos.compra.controllers.dtos.recepcion.crear.DetalleRecepcionRequest;
import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoProductoRequest;
import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoResponse;
import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.registrar.ItemMovimientoDto;
import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.registrar.RegistrarMovimientoRequest;
import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.registrar.StockMovimientoDto;
import com.dtorrez.main.modulos.inventario.mapper.RegistrarMovimientoMapper;
import com.dtorrez.main.modulos.inventario.services.stock.StockServiceImpl;
import com.dtorrez.main.modulos.producto.mappers.MovimientoInventarioProductoMapper;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.crear.DetalleVentaRequest;
import com.dtorrez.main.modulos.ventas.mapper.MovimientoInventarioVentaMapper;
import com.micrium.bd.access.jpa.modulo.inventario.models.Movimiento;
import com.micrium.bd.access.jpa.modulo.inventario.models.MovimientoProducto;
import com.micrium.bd.access.jpa.modulo.inventario.models.Stock;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IMovimientoRepository;
import com.micrium.bd.access.jpa.modulo.inventario.repository.ITipoMovimientoRepository;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RegistrarMovimientoServiceImpl implements IRegistrarMovimientoService {

    @Autowired
    private IMovimientoRepository repository;

    @Autowired
    private StockServiceImpl stockService;

    @Autowired
    private ITipoMovimientoRepository tipoMovimientoRepository;

    @Autowired
    private IProductoPresentacionRepository presentacionRepository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

    private final Logger log = LogManager.getLogger(RegistrarMovimientoServiceImpl.class);

    @Override
    @Transactional
    public MovimientoResponse registrar(MovimientoProductoRequest request) {
            final var registrarRequest = MovimientoInventarioProductoMapper
                    .fromProductoToRegistrarMovimiento.apply(request);
            log.info("Transfor de Request");
            return this.registrar(registrarRequest);
    }

    @Override
    @Transactional
    public Long registrar(List<DetalleVentaRequest> detalleVenta) {
        final var registrarRequest = MovimientoInventarioVentaMapper
                .fromDetalleVentaToRegistrarMovimiento.apply(detalleVenta);
        log.info("Transfor de Request");
        MovimientoResponse movimientoResponse = this.registrar(registrarRequest);

        if(Objects.isNull(movimientoResponse) || movimientoResponse.getId() == null) {
            throw new EntityNotFoundException("Movimiento", "Venta");
        }
        log.info("movimiento creado -> response: "+ movimientoResponse);
        return movimientoResponse.getId();
    }

    @Override
    @Transactional
    public Long registrarRecepcion(List<DetalleRecepcionRequest> detalleRecepcion) {
        final var registrarRequest = MovimientoInventarioRecepcionMapper
                .fromDetalleRecepcionToRegistrarMovimiento.apply(detalleRecepcion);
        log.info("Transfor de Request");
        MovimientoResponse movimientoResponse = this.registrar(registrarRequest);

        if(Objects.isNull(movimientoResponse) || movimientoResponse.getId() == null) {
            throw new EntityNotFoundException("Movimiento", "RecepcionProductos");
        }
        log.info("movimiento creado -> response: "+ movimientoResponse);
        return movimientoResponse.getId();
    }

    @Override
    @Transactional
    public MovimientoResponse registrar(RegistrarMovimientoRequest request) {

        validar(request);
        log.info("Valido request");

        final Long tenantId = currentUserProvider.getUserTenantId();
        final Long userId = currentUserProvider.getUserId();
        final Movimiento movimiento = RegistrarMovimientoMapper.
                fromMovimientoRequestToMovimientoEntity
                .apply(request);
        movimiento.setTenantId(tenantId);
        movimiento.setUsuarioId(userId);
        log.info("mapping to  entity");
        final List<MovimientoProducto> movimientoProductos = procesarStock(request, movimiento);
        movimiento.setDetalleMovimiento(movimientoProductos);
        log.info("Entidad Movimiento: " + movimientoProductos);

        return RegistrarMovimientoMapper.fromMovimientoEntityToMovimientoResponse
                .apply(repository.save(movimiento));
    }

    private List<MovimientoProducto> procesarStock(RegistrarMovimientoRequest request, Movimiento move) {
        log.info("procesar Caso " + request.getMotivo());

        return switch (request.getMotivo()) {
            case "REGISTRO PRODUCTO", "RECEPCION PRODUCTOS" ->
                    procesarStockFromIngresoProducto(request.getItemMovimientos(), move); // cantidad +

            case "VENTA PRODUCTOS" ->
                    procesarStockFromVentaProductos(request.getItemMovimientos(), move); // // cantidad -

            default ->
                throw new EntityNotFoundException("MotivoMovimiento","nombre", request.getMotivo());
        };
    }

    private List<MovimientoProducto> procesarStockFromIngresoProducto(
            List<ItemMovimientoDto> itemsMovimientos, Movimiento move) {
            log.info(" procesarStockFromIngresoProducto ");
          return itemsMovimientos.stream()
                .flatMap(item ->

                    item.getStocks().stream()
                            .map(stockDto -> {
                                log.info("item find/new " +  item);
                                Stock stock = stockService.resolverStock2(item, stockDto);
//                                stock.setTenantId(move.getTenantId());
                                log.info("stock find/new " +  stock);
                                return MovimientoProducto.builder()
                                        .cantidad(stockDto.getCantidad()) // base positivo
                                        .cantidadBase(stockDto.getCantidad()) // base positivo
                                        .stock(stock)
                                        .movimiento(move)
                                        .build();
                            })
                ).toList();
    }

    private List<MovimientoProducto> procesarStockFromVentaProductos(
            List<ItemMovimientoDto> itemsMovimientos, Movimiento move) {
        log.info(" procesarStockFromVentaProductos ");
        List<MovimientoProducto> newMovimientos = new ArrayList<>();

        for (ItemMovimientoDto item : itemsMovimientos) {
            int restanteAStock = item.getCantidad();

            for (StockMovimientoDto stockDto : item.getStocks()) {

                if (restanteAStock <= 0) {
                    break;
                }

                int disponibleStock = stockDto.getCantidad();
                int cantidadADescontar = Math.min(disponibleStock, restanteAStock);

                newMovimientos.add(crearMovimientoProducto(item, stockDto, cantidadADescontar, move));

                restanteAStock -= cantidadADescontar;
            }
        }
        return newMovimientos;
    }

    private MovimientoProducto crearMovimientoProducto(
            ItemMovimientoDto item,
            StockMovimientoDto stockDto,
            int cantidad,
            Movimiento move
    ) {
        Stock stock =
                stockService.resolverStock2(item, stockDto);
//        stock.setTenantId(move.getTenantId());

        return MovimientoProducto.builder()
                .cantidad(cantidad)
                .cantidadBase(-cantidad) // Negativo SALIDA
                .stock(stock)
                .movimiento(move)
                .build();
    }

    private void validar(RegistrarMovimientoRequest request) {

        tipoMovimientoRepository.findById(request.getTipoMovimientoId())
                .orElseThrow(() -> new EntityNotFoundException("Tipo Movimiento", "id", request.getTipoMovimientoId()));

        request.getItemMovimientos().forEach(this::validarItems);
    }

    private void validarItems(ItemMovimientoDto item) {
        presentacionRepository.findByIdAndProductoId(item.getPresentacionId(), item.getProductoId())
                .orElseThrow(() -> new EntityNotFoundException("Presentacion","id", item.getPresentacionId()));
    }
}
