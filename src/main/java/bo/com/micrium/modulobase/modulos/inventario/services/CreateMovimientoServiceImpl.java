package bo.com.micrium.modulobase.modulos.inventario.services;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.DetalleMovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.MovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.MovimientoResponse;
import bo.com.micrium.modulobase.modulos.inventario.mapper.MovimientoMapper;
import com.micrium.bd.access.jpa.modulo.inventario.models.Movimiento;
import com.micrium.bd.access.jpa.modulo.inventario.models.MovimientoProducto;
import com.micrium.bd.access.jpa.modulo.inventario.models.Stock;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IMovimientoProductoRepository;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IMovimientoRepository;
import com.micrium.bd.access.jpa.modulo.inventario.repository.ITipoMovimientoRepository;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CreateMovimientoServiceImpl implements ICreateMovimientoService {

    @Autowired
    private IMovimientoRepository repository;

    @Autowired
    private IMovimientoProductoRepository detalleRepository;

    @Autowired
    private StockServiceImpl stockService;

    @Autowired
    private IProductoPresentacionRepository presentacionRepository;

    @Autowired
    private ITipoMovimientoRepository tipoMovimientoRepository;

    private final Logger log = LogManager.getLogger(CreateMovimientoServiceImpl.class);

    @Override
    @Transactional
    public MovimientoResponse execute(MovimientoRequest request) {
        log.info("execute create");
        this.validateMovimiento(request);
        log.info("is validate create");
        final Movimiento movimiento = MovimientoMapper.
                fromMovimientoRequestToMovimientoEntity
                .apply(request);

        this.log.info("Request: "+ request);
        this.log.info("model movimiento before: " + movimiento);

        List<MovimientoProducto> detalles = IntStream.range(0, request.getDetalleMovimiento().size())
                .mapToObj(i -> {

                    DetalleMovimientoRequest dto = request.getDetalleMovimiento().get(i);
                    MovimientoProducto detalle = movimiento.getDetalleMovimiento().get(i);

                    //validateDetalleMovimiento(request, dto);
                    Stock stock = stockService.resolverStock(request, dto);

                    if(esMovimientoTipoSalida(request)) {
                        //stockService.hayStockDisponible(stock.getId(), dto.getCantidadStockBase());
                        System.out.println("Validar stock");
                    }

                    this.log.info("model stock: " + stock);
                    detalle.setCantidad(dto.getCantidadStockBase());
                    detalle.setCantidadBase(getCantidadBaseConSigno(request,dto));
                    detalle.setStock(stock);
                    return detalle;
                })
                .collect(Collectors.toList());

        movimiento.setDetalleMovimiento(detalles);
        this.log.info("model movimiento after: " + movimiento);
        return MovimientoMapper.fromMovimientoEntityToMovimientoResponse
                .apply(repository.save(movimiento));
    }

    private void validateMovimiento(MovimientoRequest movimiento) {
        presentacionRepository.findByIdAndProductoId(movimiento.getPresentacionId(), movimiento.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto Presentacion no existe."));

        tipoMovimientoRepository.findById(movimiento.getTipoMovimientoId())
                .orElseThrow(() -> new RuntimeException("Tipo Movimiento no existe."));

        if (esMovimientoTipoEntrada(movimiento) &&
            Objects.nonNull(movimiento.getUbicacionStockId())) {
            stockService.validateUbicacionStock(movimiento.getUbicacionStockId());
        }
    }

    private boolean esMovimientoTipoSalida(MovimientoRequest movimiento) {
        return movimiento.getTipoMovimientoId().equals(2L);
    }

    private Integer getCantidadBaseConSigno(MovimientoRequest request, DetalleMovimientoRequest dto) {
        if (esMovimientoTipoEntrada(request)) {
            return dto.getCantidadStockBase();
        } else {
            return dto.getCantidadStockBase() * -1;
        }
    }

    private boolean esMovimientoTipoEntrada(MovimientoRequest movimiento) {
        return movimiento.getTipoMovimientoId().equals(1L);
    }
}
