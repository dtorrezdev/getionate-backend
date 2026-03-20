package bo.com.micrium.modulobase.modulos.ventas.mapper;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.cliente.ListClienteRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.cliente.ListClienteResponse;
import com.micrium.bd.access.jpa.modulo.venta.models.Cliente;

import java.util.function.Function;

public class ClienteMapper {

    public static final Function<ListClienteRequest, Cliente> toEntity = req ->
            Cliente.builder()
                    .ci(req.getCi())
                    .nombre(req.getNombre())
                    .celular(req.getCelular())
                    .build();

    public static final Function<Cliente, ListClienteResponse> toResponse =
            m -> new ListClienteResponse(m.getId(), m.getCi(), m.getNombre(), m.getCelular());
}
