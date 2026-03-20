package bo.com.micrium.modulobase.modulos.ventas.services.cliente;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.cliente.ListClienteRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.cliente.ListClienteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClienteService {

    Page<ListClienteResponse> list(ListClienteRequest request, Pageable page);
}
