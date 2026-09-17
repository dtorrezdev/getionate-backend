package com.dtorrez.main.modulos.ventas.mapper;

import com.dtorrez.main.modulos.ventas.controllers.dtos.cliente.ClienteRequest;
import com.dtorrez.main.modulos.ventas.controllers.dtos.cliente.ClienteResponse;
import com.micrium.bd.access.jpa.modulo.venta.models.Cliente;

import java.util.function.Function;

public class ClienteMapper {

    public static final Function<ClienteRequest, Cliente> fromClienteRequestToEntity = req ->
            Cliente.builder()
                    .ci(req.getCi())
                    .nombre(req.getNombre())
                    .celular(req.getCelular())
                    .build();

    public static final Function<Cliente, ClienteResponse> fromEntityToClientResponse =
            m -> new ClienteResponse(m.getId(), m.getCi(), m.getNombre(), m.getCelular());
}
