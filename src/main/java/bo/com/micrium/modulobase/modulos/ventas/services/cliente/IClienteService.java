package bo.com.micrium.modulobase.modulos.ventas.services.cliente;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.cliente.ClienteRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.cliente.ListClienteRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.cliente.ClienteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClienteService {

    Page<ClienteResponse> list(ListClienteRequest request, Pageable page);

    ClienteResponse create(ClienteRequest request);

    ClienteResponse update(ClienteRequest request, Long clienteId);

    void delete(Long clienteId);
}
