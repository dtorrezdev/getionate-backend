package bo.com.micrium.modulobase.modulos.inventario.services.movimiento.listar;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.list.ListMovimientoProductoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.list.ListMovimientoProductoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IListarMovimientoProductoService {

    Page<ListMovimientoProductoResponse> listar(ListMovimientoProductoRequest request, Pageable page);
}
