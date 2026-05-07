package bo.com.micrium.modulobase.modulos.inventario.services.stock;

import bo.com.micrium.modulobase.common.enums.EnumVenta;
import bo.com.micrium.modulobase.common.exceptions.BusinessRuleException;
import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.DetalleMovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoProductoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.ItemMovimientoDto;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.StockMovimientoDto;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleDto;
import bo.com.micrium.modulobase.modulos.inventario.mapper.StockMapper;
import com.micrium.bd.access.jpa.modulo.inventario.models.Stock;
import com.micrium.bd.access.jpa.modulo.inventario.models.UbicacionStock;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IStockRepository;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IUbicacionStockRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements IStockService {

    private final IStockRepository repository;
    private final IUbicacionStockRepository ubicacionStockRepository;

    private final Logger log = LogManager.getLogger(StockServiceImpl.class);

    @Override
    public Map<Long, List<StockDisponibleDto>> stockDisponibles() {
        log.info("call stockDisponibles");
        //

        final List<StockDisponibleDto> stocks = repository.listAll()
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
        final Optional<UbicacionStock> byId = ubicacionStockRepository.findById(request.getUbicacionStockId());
        log.info("create new stock lote: " + detalle.getLote() + " presentacionId: " + request.getPresentacionId());
        Stock newStock = Stock.builder()
                .lote(detalle.getLote())
                .expiracion(detalle.getFechaExpiracion())
                .registroSanitario(detalle.getRegistroSanitario())
                .productoId(request.getProductoId())
                .presentacionId(request.getPresentacionId())
                .ubicacionStock(byId.orElse(null))
                .build();
        return repository.save(newStock);
    }

    public Stock save2(ItemMovimientoDto request, StockMovimientoDto stockDto) {
        final Optional<UbicacionStock> ubicacionStock = ubicacionStockRepository.findById(stockDto.getUbicacionStockId());
        log.info("create new stock lote: " + stockDto.getLote() + " presentacionId: " + request.getPresentacionId());
        Stock newStock = Stock.builder()
                .lote(stockDto.getLote())
                .expiracion(stockDto.getExpiracion())
                .registroSanitario(stockDto.getRegistroSanitario())
                .productoId(request.getProductoId())
                .presentacionId(request.getPresentacionId())
                .ubicacionStock(ubicacionStock.orElse(null))
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
        //log.info("result " + stocks);

        return repository.findByPresentacionId(presentacionId)
                .stream()
                .map(StockMapper.toResponse)
                .toList();
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

    public StockServiceImpl(
            IStockRepository repository,
            IUbicacionStockRepository ubicacionStockRepository
    ) {
        this.repository = repository;
        this.ubicacionStockRepository = ubicacionStockRepository;
    }
}
