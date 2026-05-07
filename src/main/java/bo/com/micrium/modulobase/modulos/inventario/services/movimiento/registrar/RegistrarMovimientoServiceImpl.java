package bo.com.micrium.modulobase.modulos.inventario.services.movimiento.registrar;

import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.ItemMovimientoDto;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.RegistrarMovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.registrar.StockMovimientoDto;
import bo.com.micrium.modulobase.modulos.inventario.mapper.RegistrarMovimientoMapper;
import bo.com.micrium.modulobase.modulos.inventario.services.stock.StockServiceImpl;
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

    private final Logger log = LogManager.getLogger(RegistrarMovimientoServiceImpl.class);

    @Override
    @Transactional
    public void registrar(RegistrarMovimientoRequest request) {

        validar(request);
        log.info("Valido request");

        final Movimiento movimiento = RegistrarMovimientoMapper.
                fromMovimientoRequestToMovimientoEntity
                .apply(request);
        log.info("mapping to  entity");
        final List<MovimientoProducto> movimientoProductos = procesarStock(request);
        movimiento.setDetalleMovimiento(movimientoProductos);
        log.info("Entidad Movimiento: " + movimientoProductos);

        //repository.save(movimiento);
    }

    private List<MovimientoProducto> procesarStock(RegistrarMovimientoRequest request) {
        System.out.println("procesar Caso " + request.getMotivo());
        return switch (request.getMotivo()) {
            case "REGISTRO PRODUCTO" ->
                    procesarStockFromRegistroProducto(request.getItemMovimientos()); // cantidad +

            case "RECEPCION PRODUCTOS" ->
                    procesarStockFromRecepcionProductos(request.getItemMovimientos()); // cantidad +

            case "VENTA PRODUCTOS" ->
                    procesarStockFromVentaProductos(request.getItemMovimientos()); // // cantidad -

            default ->
                throw new EntityNotFoundException("MotivoMovimiento","nombre", request.getMotivo());
        };
    }

    private List<MovimientoProducto> procesarStockFromRegistroProducto(List<ItemMovimientoDto> itemsMovimientos) {

          return itemsMovimientos.stream()
                .flatMap(item ->
                    item.getStocks().stream()
                            .map(stockDto -> {
                                Stock stock = stockService.resolverStock2(item, stockDto);

                                return MovimientoProducto.builder()
                                        .cantidad(stockDto.getCantidad()) // base positivo
                                        .cantidadBase(stockDto.getCantidad()) // base positivo
                                        .stock(stock)
                                        .build();
                            })
                ).toList();
    }

    private List<MovimientoProducto> procesarStockFromVentaProductos(List<ItemMovimientoDto> itemsMovimientos) {
        List<MovimientoProducto> newMovimientos = new ArrayList<>();

        for (ItemMovimientoDto item : itemsMovimientos) {
            int restanteAStock = item.getCantidad();

            for (StockMovimientoDto stockDto : item.getStocks()) {

                if (restanteAStock <= 0) {
                    break;
                }

                int disponibleStock = stockDto.getCantidad();
                int cantidadADescontar = Math.min(disponibleStock, restanteAStock);

                newMovimientos.add(crearMovimientoProducto(item, stockDto, cantidadADescontar));

                restanteAStock -= cantidadADescontar;
            }
        }
        return newMovimientos;
    }

    private List<MovimientoProducto> procesarStockFromRecepcionProductos(List<ItemMovimientoDto> itemsMovimientos) {

        return null;
    }

    private MovimientoProducto crearMovimientoProducto(ItemMovimientoDto item, StockMovimientoDto stockDto, int cantidad) {
        Stock stock =
                stockService.resolverStock2(item, stockDto);

        return MovimientoProducto.builder()
                .cantidad(cantidad)
                .cantidadBase(-cantidad) // Negativo SALIDA
                .stock(stock)
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
