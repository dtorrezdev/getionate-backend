package bo.com.micrium.modulobase.modulos.inventario.services;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.DetalleMovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.MovimientoRequest;
import com.micrium.bd.access.jpa.modulo.inventario.models.Stock;
import com.micrium.bd.access.jpa.modulo.inventario.models.UbicacionStock;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IStockRepository;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IUbicacionStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockServiceImpl {

    @Autowired
    private IStockRepository repository;

    @Autowired
    private IUbicacionStockRepository ubicacionStockRepository;

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

    public Stock resolverStock(MovimientoRequest request, DetalleMovimientoRequest detalle) {

        // if(!validateUbicacionStock(request.getUbicacionStockId())) return null;
        return repository.findByLoteAndProductoIdAndPresentacionId(detalle.getLote(),
                request.getProductoId(), request.getPresentacionId())
                .orElseGet(() -> save(request, detalle));
    }

    public boolean validateUbicacionStock(Long ubicacionStockId) {
        ubicacionStockRepository.findById(ubicacionStockId)
                .orElseThrow(() -> new RuntimeException("Ubicacion Stock no existe."));
        return true;
    }
}
