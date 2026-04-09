package bo.com.micrium.modulobase.modulos.inventario.services.stock;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleByProductoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleByProductoResponse;
import bo.com.micrium.modulobase.modulos.inventario.mapper.StockMapper;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IMovimientoProductoRepository;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IStockRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListStockDisponibleService {

    @Autowired
    private IStockRepository repository;

    @Autowired
    private IMovimientoProductoRepository detalleMovimientoRepository;

    private final Logger log = LogManager.getLogger(ListStockDisponibleService.class);

    public List<StockDisponibleByProductoResponse> execute(StockDisponibleByProductoRequest request) {
        log.info("request " + request);
        //log.info("result " + stocks);

        return repository.findByProductoIdAndPresentacionId(request.getProductoId(), request.getPresentacionId())
                .stream()
                .map(StockMapper.toResponse)
                .toList();
    }
}
