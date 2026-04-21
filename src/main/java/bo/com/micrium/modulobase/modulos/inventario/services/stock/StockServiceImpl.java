package bo.com.micrium.modulobase.modulos.inventario.services.stock;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.DetalleMovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.producto.MovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleDto;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleResponse;
import bo.com.micrium.modulobase.modulos.inventario.mapper.StockMapper;
import com.micrium.bd.access.jpa.modulo.inventario.models.Stock;
import com.micrium.bd.access.jpa.modulo.inventario.models.UbicacionStock;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IMovimientoProductoRepository;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IStockRepository;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IUbicacionStockRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
    public Stock save(MovimientoRequest request, DetalleMovimientoRequest detalle) {
        final Optional<UbicacionStock> byId = ubicacionStockRepository.findById(request.getUbicacionStockId());
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

    @Override
    public Stock resolverStock(MovimientoRequest request, DetalleMovimientoRequest detalle) {

        // if(!validateUbicacionStock(request.getUbicacionStockId())) return null;
        return repository.findByLoteAndProductoIdAndPresentacionId(detalle.getLote(),
                request.getProductoId(), request.getPresentacionId())
                .orElseGet(() -> save(request, detalle));
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
                .orElseThrow(() -> new RuntimeException("Ubicacion Stock no existe."));
        return true;
    }

    @Deprecated
    @Override
    public boolean hayStockDisponibleByProductoId(Long productoId, Long presentacionId, Integer cantidadAVender) {

        final Integer cantidadDisponibleStock = repository.getCantidadStockDisponibleByProducto(productoId, presentacionId);

        if(cantidadAVender > cantidadDisponibleStock) {
            throw new RuntimeException("Stock insuficiente del producto PR-"+presentacionId);
        }
        return true;
    }


    @Override
    public boolean hayStockDisponibleByPresentacionId(Long presentacionId, Integer cantidadAVender) {
        final Integer cantidadDisponibleStock = repository.getCantidadStockDisponibleByPresentacionId(presentacionId);

        if(cantidadAVender > cantidadDisponibleStock) {
            throw new RuntimeException("Stock insuficiente del producto PR-"+presentacionId);
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
