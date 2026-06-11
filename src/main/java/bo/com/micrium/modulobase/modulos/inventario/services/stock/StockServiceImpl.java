package bo.com.micrium.modulobase.modulos.inventario.services.stock;

import bo.com.micrium.modulobase.common.enums.EnumEvento;
import bo.com.micrium.modulobase.common.enums.EnumVenta;
import bo.com.micrium.modulobase.common.exceptions.BusinessRuleException;
import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.common.providers.CurrentUserProvider;
import bo.com.micrium.modulobase.modulos.evento.services.IEventoNotificacionService;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.DetalleMovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoProductoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.ItemMovimientoDto;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.StockMovimientoDto;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleDto;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleResponse;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockUpdateRequest;
import bo.com.micrium.modulobase.modulos.inventario.mapper.StockMapper;
import com.micrium.bd.access.jpa.modulo.eventos.models.EventoNotificacion;
import com.micrium.bd.access.jpa.modulo.inventario.models.Stock;
import com.micrium.bd.access.jpa.modulo.inventario.models.UbicacionStock;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IStockRepository;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IUbicacionStockRepository;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements IStockService {

    private final IStockRepository repository;
    private final IProductoPresentacionRepository presentacionRepository;
    private final IUbicacionStockRepository ubicacionStockRepository;
    private final CurrentUserProvider currentUserProvider;

    // Dominio del Modulo Evento
    private final IEventoNotificacionService eventoService;

    private final Logger log = LogManager.getLogger(StockServiceImpl.class);

    @Override
    public Map<Long, List<StockDisponibleDto>> stockDisponibles() {
        log.info("call stockDisponibles");
        final Long tenantId = currentUserProvider.getUserTenantId();
        final List<StockDisponibleDto> stocks = repository.listAll(tenantId)
                .stream()
                .map(StockMapper.toResponse)
                .toList();
        log.info("stocks.size() " + stocks.size());
        return stocks
                .stream()
                .collect(
                        Collectors.groupingBy(StockDisponibleDto::getPresentacionId)
                );
    }

    @Override
    public Stock save(MovimientoProductoRequest request, DetalleMovimientoRequest detalle) {
        var ubicacionStock = Optional.<UbicacionStock>empty();
        final Long tenantId = currentUserProvider.getUserTenantId();

        if(Objects.nonNull(request.getUbicacionStockId())) {
            ubicacionStock = ubicacionStockRepository.findById(request.getUbicacionStockId());
        }

        log.info("create new stock lote: " + detalle.getLote() + " presentacionId: " + request.getPresentacionId());
        Stock newStock = Stock.builder()
                .lote(detalle.getLote())
                .expiracion(detalle.getFechaExpiracion())
                .registroSanitario(detalle.getRegistroSanitario())
                .productoId(request.getProductoId())
                .presentacionId(request.getPresentacionId())
                .ubicacionStock(ubicacionStock.orElse(null))
                .tenantId(tenantId)
                .build();
        return repository.save(newStock);
    }

    public Stock save2(ItemMovimientoDto request, StockMovimientoDto stockDto) {
        var ubicacionStock = Optional.<UbicacionStock>empty();
        final Long tenantId = currentUserProvider.getUserTenantId();

        if(Objects.nonNull(stockDto.getUbicacionStockId())) {
            ubicacionStock = ubicacionStockRepository.findById(stockDto.getUbicacionStockId());
        }
        log.info("create new stock lote: " + stockDto.getLote() + " presentacionId: " + request.getPresentacionId());
        Stock newStock = Stock.builder()
                .lote(stockDto.getLote())
                .expiracion(stockDto.getExpiracion())
                .registroSanitario(stockDto.getRegistroSanitario())
                .productoId(request.getProductoId())
                .presentacionId(request.getPresentacionId())
                .ubicacionStock(ubicacionStock.orElse(null))
                .tenantId(tenantId)
                .build();
        return repository.save(newStock);
    }

    @Override
    public Stock resolverStock(MovimientoProductoRequest request, DetalleMovimientoRequest stockDto) {

        // if(!validateUbicacionStock(request.getUbicacionStockId())) return null;
        return repository.findByLoteAndProductoIdAndPresentacionId(stockDto.getLote(),
                request.getProductoId(), request.getPresentacionId())
                .orElseGet(() -> save(request, stockDto));
    }

    public Stock resolverStock2(ItemMovimientoDto request, StockMovimientoDto stockDto) {
        if(Objects.nonNull(stockDto.getId())) {
            return repository.findById(stockDto.getId())
                    .orElseGet(() -> save2(request, stockDto));
        }

        return repository.findByLoteAndProductoIdAndPresentacionId(stockDto.getLote(),
                        request.getProductoId(), request.getPresentacionId())
                .orElseGet(() -> save2(request, stockDto));
    }

    @Override
    public List<StockDisponibleDto> stockDisponibleByPresentacionId(Long presentacionId) {
        log.info("request presentacionId: " + presentacionId);

        var presentacion = presentacionRepository.findById(presentacionId)
                .orElseThrow(() -> new EntityNotFoundException("Presentacion", "id", presentacionId));

        final List<StockDisponibleDto> list = repository.findByPresentacionId(presentacionId)
                .stream()
                .map(StockMapper.toResponse)
                .toList();
        list.forEach(stock-> {
            var fechaExp = stock.getExpiracion();
            var hoy = new Date(System.currentTimeMillis());
            if(Objects.nonNull(fechaExp)) {
                int diffInDays = (int)( (fechaExp.getTime() - hoy.getTime())
                        / (1000 * 60 * 60 * 24) );
                if(diffInDays <= presentacion.getDiasAntesExpiracion()) {
                    System.out.println("Alerta stock fecha proxima a vencer");
                 // TODO: llamar a servicio notificacion y registras
                 // * tomar en cuenta si ya se ha registrado esta notificacion
                    EventoNotificacion event = eventoService.registrarEvento(EnumEvento.Type.PROD_PROXIMO_A_EXPIRAR.name(), presentacion.getId());
                    log.info("Se creado evento type PROD_PROXIMO_A_EXPIRAR: " + event);
                }
            }
        });
        eventoService.procesarEventosPendientes();
        return list;
    }

    @Override
    public boolean validateUbicacionStock(Long ubicacionStockId) {
        ubicacionStockRepository.findById(ubicacionStockId)
                .orElseThrow(() -> new EntityNotFoundException("Ubicacion Stock", "id", ubicacionStockId));
        return true;
    }

    @Deprecated
    @Override
    public boolean hayStockDisponibleByProductoId(Long productoId, Long presentacionId, Integer cantidadAVender) {

        final Integer cantidadDisponibleStock = repository.getCantidadStockDisponibleByProducto(productoId, presentacionId);

        if(cantidadAVender > cantidadDisponibleStock) {
            throw new BusinessRuleException("Presentacion",
                    EnumVenta.Rules.STOCK_INSUFICIENTE.name() ,
                    Map.of("id", presentacionId,"disponible", cantidadDisponibleStock, "cantidad", cantidadAVender)
            );
        }
        return true;
    }


    @Override
    public boolean hayStockDisponibleByPresentacionId(Long presentacionId, Integer cantidadAVender) {
        final Integer cantidadDisponibleStock = repository.getCantidadStockDisponibleByPresentacionId(presentacionId);

        if(cantidadAVender > cantidadDisponibleStock) {
            throw new BusinessRuleException("Presentacion",
                    EnumVenta.Rules.STOCK_INSUFICIENTE.name() ,
                    Map.of("id", presentacionId,"disponible", cantidadDisponibleStock, "cantidad", cantidadAVender)
            );
        }
        return true;
    }

    @Override
    public StockDisponibleResponse update(StockUpdateRequest request, Long presentacionId) {
        log.info("update stocks: " + request);

        this.presentacionRepository.findById(presentacionId)
                .orElseThrow(() -> new EntityNotFoundException("Presentacion", "id", presentacionId));

        final UbicacionStock ubicacionStock = this.ubicacionStockRepository.findById(request.getUbicacionStockId())
                .orElseThrow(() -> new EntityNotFoundException("Ubicacion Stock", "id", request.getUbicacionStockId()));

        log.info("valid request true");
        final List<Stock> list = request.getStocks().stream()
                .map(stock -> {
                    final Stock stockUpdated = this.repository.findById(stock.getId())
                            .orElseThrow(() -> new EntityNotFoundException("Stock", "id", stock.getId()));
                    stockUpdated.setExpiracion(stock.getExpiracion());
                    stockUpdated.setRegistroSanitario(stock.getRegistroSanitario());
                    stockUpdated.setUbicacionStock(ubicacionStock);
                    return stockUpdated;
                }).toList();

         this.repository.saveAll(list);
        return new StockDisponibleResponse();
    }

    public StockServiceImpl(
            IStockRepository repository,
            IProductoPresentacionRepository presentacionRepository,
            IUbicacionStockRepository ubicacionStockRepository,
            IEventoNotificacionService eventoService,
            CurrentUserProvider currentUserProvider
    ) {
        this.repository = repository;
        this.presentacionRepository = presentacionRepository;
        this.ubicacionStockRepository = ubicacionStockRepository;
        this.eventoService = eventoService;
        this.currentUserProvider = currentUserProvider;
    }
}
