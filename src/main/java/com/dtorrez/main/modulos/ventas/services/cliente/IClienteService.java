package com.dtorrez.main.modulos.ventas.services.cliente;

import com.dtorrez.main.modulos.ventas.controllers.dtos.cliente.ClienteRequest;
import com.dtorrez.main.modulos.ventas.controllers.dtos.cliente.ListClienteRequest;
import com.dtorrez.main.modulos.ventas.controllers.dtos.cliente.ClienteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClienteService {

    Page<ClienteResponse> list(ListClienteRequest request, Pageable page);

    ClienteResponse create(ClienteRequest request);

    ClienteResponse update(ClienteRequest request, Long clienteId);

    void delete(Long clienteId);
}
