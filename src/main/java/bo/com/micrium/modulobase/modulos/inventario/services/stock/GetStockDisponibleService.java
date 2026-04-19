package bo.com.micrium.modulobase.modulos.inventario.services.stock;

import bo.com.micrium.modulobase.common.enums.EnumEvento;
import bo.com.micrium.modulobase.modulos.evento.services.EventoNotificaconServiceImpl;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleResponse;
import bo.com.micrium.modulobase.modulos.inventario.mapper.StockMapper;
import com.micrium.bd.access.jpa.modulo.inventario.projection.StockDisponibleProjection;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IMovimientoProductoRepository;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IStockRepository;
import com.micrium.bd.access.jpa.modulo.productos.models.ProductoPresentacion;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GetStockDisponibleService {

    @Autowired
    private IStockRepository repository;

    @Autowired
    private IMovimientoProductoRepository detalleMovimientoRepository;

    private final Logger log = LogManager.getLogger(GetStockDisponibleService.class);

    public List<StockDisponibleResponse> execute(StockDisponibleRequest request) {
        log.info("request " + request);
        //log.info("result " + stocks);

        return repository.findByProductoIdAndPresentacionId(request.getProductoId(), request.getPresentacionId())
                .stream()
                .map(StockMapper.toResponse)
                .toList();
    }
}
