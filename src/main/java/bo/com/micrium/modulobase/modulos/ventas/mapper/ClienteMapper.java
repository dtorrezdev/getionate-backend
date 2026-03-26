package bo.com.micrium.modulobase.modulos.ventas.mapper;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.cliente.ClienteRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.cliente.ListClienteRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.cliente.ClienteResponse;
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
