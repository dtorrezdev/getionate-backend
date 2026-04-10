package bo.com.micrium.modulobase.modulos.inventario.services.movimiento;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.venta.DetalleMovimientoVentaRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.venta.MovimientoVentaRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.venta.MovimientoVentaResponse;
import bo.com.micrium.modulobase.modulos.inventario.mapper.MovimientoVentaMapper;
import bo.com.micrium.modulobase.modulos.inventario.services.stock.StockServiceImpl;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.DetalleVentaRequest;
import com.micrium.bd.access.jpa.modulo.inventario.models.Movimiento;
import com.micrium.bd.access.jpa.modulo.inventario.models.MovimientoProducto;
import com.micrium.bd.access.jpa.modulo.inventario.models.Stock;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IMovimientoRepository;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IStockRepository;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CreateMovientoVentaServiceImpl implements ICreateMovimientoVentaService {

    @Autowired
    private IMovimientoRepository repository;

    @Autowired
    private StockServiceImpl stockService;

    @Autowired
    private IProductoPresentacionRepository presentacionRepository;

    @Autowired
    private IStockRepository stockRepository;

    private final Logger log = LogManager.getLogger(CreateMovientoVentaServiceImpl.class);

    @Override
    @Transactional
    public MovimientoVentaResponse execute(List<DetalleVentaRequest> request) {

        this.validateDetalle(request);
        log.info("final validate");
        final MovimientoVentaRequest requestMovimiento = MovimientoVentaMapper.fromVentaRequestToMovimiento(request);

        final Movimiento movimiento = MovimientoVentaMapper.
                fromMovimientoRequestToMovimientoEntity
                .apply(requestMovimiento);

        List<MovimientoProducto> detalles = IntStream.range(0, requestMovimiento.getDetalleMovimiento().size())
                .mapToObj(i -> {

                    DetalleMovimientoVentaRequest dto = requestMovimiento.getDetalleMovimiento().get(i);
                    MovimientoProducto detalle = movimiento.getDetalleMovimiento().get(i);

                    //validateDetalleMovimiento(request, dto);
                    Stock stock = stockRepository.findById(dto.getStockId())
                            .orElseThrow(()-> new RuntimeException("Stock Id no existe"));

                    this.log.info("model stock: " + stock);
                    detalle.setCantidad(dto.getCantidadStock());
                    detalle.setCantidadBase(getCantidadBaseConSigno(requestMovimiento,dto));
                    detalle.setStock(stock);
                    return detalle;
                })
                .collect(Collectors.toList());

        movimiento.setDetalleMovimiento(detalles);
        log.info("movimiento entity: " + movimiento);

        return MovimientoVentaMapper.fromEntityToResponse
                .apply(repository.save(movimiento));
    }

    private void validateDetalle(List<DetalleVentaRequest> request) {
        log.info("validateDetalle");

        request.forEach((detalle) -> {
            presentacionRepository.findByIdAndProductoId(
                    detalle.getPresentacionId(), detalle.getProductoId())
                    .orElseThrow(() ->
                            new RuntimeException("Producto Presentacion no existe."));

            detalle.getStocks().forEach(stock-> {
                stockRepository.findById(stock.getId())
                        .orElseThrow(()->
                                new RuntimeException("Stock Id no existe"));
            });

            final Integer cantidadStockDisponible = this.stockRepository
                    .getCantidadStockDisponibleByProducto(detalle.getProductoId(),
                    detalle.getPresentacionId());
            log.info("cantidadAvender: " + detalle.getCantidad() + ", cantidadDisponibleStock: "+cantidadStockDisponible);
            if(detalle.getCantidad() > cantidadStockDisponible) {
                throw new RuntimeException("Stock insuficiente del producto PR-" + detalle.getPresentacionId());
            }
        });
    }

    private Integer getCantidadBaseConSigno(MovimientoVentaRequest request, DetalleMovimientoVentaRequest dto) {
        if (esMovimientoTipoEntrada(request)) {
            return dto.getCantidadStockBase();
        } else {
            return dto.getCantidadStockBase() * -1;
        }
    }

    private boolean esMovimientoTipoEntrada(MovimientoVentaRequest movimiento) {
        return movimiento.getTipoMovimientoId().equals(1L);
    }
}
