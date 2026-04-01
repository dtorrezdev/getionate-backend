package bo.com.micrium.modulobase.modulos.ventas.services.venta;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.get.GetVentaResponse;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.list.ListVentaRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.list.ListVentaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IGetVentaService {

    GetVentaResponse execute(Long ventaId);
}
