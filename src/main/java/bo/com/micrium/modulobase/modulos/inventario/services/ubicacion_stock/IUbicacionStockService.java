package bo.com.micrium.modulobase.modulos.inventario.services.ubicacion_stock;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.ubicacion_stock.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUbicacionStockService {

    Page<UbicacionStockResponse> list(UbicacionStockRequest request, Pageable page);

    UbicacionStockResponse create(UbicacionStockRequest request);

    UbicacionStockResponse update(UbicacionStockRequest request, Long id);

    void delete(Long id);
}
