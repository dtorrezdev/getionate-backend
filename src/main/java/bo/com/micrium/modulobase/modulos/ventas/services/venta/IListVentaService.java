package bo.com.micrium.modulobase.modulos.ventas.services.venta;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.ListVentaRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.ListVentaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface IListVentaService {

    Page<ListVentaResponse> execute(ListVentaRequest params, Pageable page);

}
