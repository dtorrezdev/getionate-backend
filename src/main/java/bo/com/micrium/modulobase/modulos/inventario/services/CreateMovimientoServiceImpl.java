package bo.com.micrium.modulobase.modulos.inventario.services;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.DetalleMovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.MovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.MovimientoResponse;
import bo.com.micrium.modulobase.modulos.inventario.mapper.MovimientoMapper;
import com.micrium.bd.access.jpa.modulo.inventario.models.Movimiento;
import com.micrium.bd.access.jpa.modulo.inventario.models.MovimientoProducto;
import com.micrium.bd.access.jpa.modulo.inventario.models.Stock;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IMovimientoProductoRepository;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IMovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CreateMovimientoServiceImpl implements ICreateMovimientoService {

    @Autowired
    private IMovimientoRepository repository;

    @Autowired
    private IMovimientoProductoRepository detalleRepository;

    @Override
    public MovimientoResponse execute(MovimientoRequest request) {
        // validateMovimiento(request)
        final Movimiento movimiento = MovimientoMapper.
                fromMovimientoRequestToMovimientoEntity
                .apply(request);

        List<MovimientoProducto> detalles = IntStream.range(0, request.getDetalleMovimiento().size())
                .mapToObj(i -> {

                    DetalleMovimientoRequest dto = request.getDetalleMovimiento().get(i);
                    MovimientoProducto detalle = movimiento.getDetalleMovimiento().get(i);

                    Stock stock = Stock.builder()
                            .lote(dto.getLote())
                            .expiracion(dto.getFechaExpiracion())
                            .registroSanitario(dto.getRegistroSanitario())
                            .productoId(request.getProductoId())
                            .presentacionId(request.getPresentacionId())
                            .ubicacionStockId(request.getUbicacionStockId())
                            .build();

                    detalle.setStock(stock);

                    return detalle;
                })
                .collect(Collectors.toList());

        movimiento.setDetalleMovimiento(detalles);

        return MovimientoMapper.fromMovimientoEntityToMovimientoResponse
                .apply(repository.save(movimiento));
    }

    private boolean validateMovimiento(MovimientoRequest movimiento) {

        return true;
    }
}
