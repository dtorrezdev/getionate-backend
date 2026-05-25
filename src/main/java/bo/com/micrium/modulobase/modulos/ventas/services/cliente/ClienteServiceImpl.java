package bo.com.micrium.modulobase.modulos.ventas.services.cliente;

import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.common.providers.CurrentUserProvider;
import bo.com.micrium.modulobase.modulos.producto.mappers.MarcaMapper;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.cliente.ClienteRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.cliente.ListClienteRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.cliente.ClienteResponse;
import bo.com.micrium.modulobase.modulos.ventas.mapper.ClienteMapper;
import com.micrium.bd.access.jpa.modulo.productos.models.Marca;
import com.micrium.bd.access.jpa.modulo.venta.models.Cliente;
import com.micrium.bd.access.jpa.modulo.venta.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private IClienteRepository repository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

    @Override
    public Page<ClienteResponse> list(ListClienteRequest request, Pageable page) {

        final Long tenantId = currentUserProvider.getUserTenantId();

        return repository.filter(
                queryfilterTexto(request.getCi()),
                filterTextoQueryUpperLike(request.getCi()),
                queryfilterTexto(request.getNombre()),
                filterTextoQueryUpperLike(request.getNombre()),
                queryfilterTexto(request.getCelular()),
                filterTextoQueryUpperLike(request.getCelular()),
                page, tenantId)
                .map(ClienteMapper.fromEntityToClientResponse);
    }

    @Override
    public ClienteResponse create(ClienteRequest request) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        final Cliente newCliente = ClienteMapper.fromClienteRequestToEntity.apply(request);
        newCliente.setTenantId(tenantId);

        return ClienteMapper.fromEntityToClientResponse
                .apply(repository.save(newCliente));
    }

    @Override
    public ClienteResponse update(ClienteRequest request, Long clienteId) {
        return repository.findById(clienteId)
                .map(clienteUpdated -> {
                    clienteUpdated.setNombre(request.getNombre());
                    clienteUpdated.setCi(request.getCi());
                    clienteUpdated.setCelular(request.getCelular());
                    return clienteUpdated;
                })
                .map(repository::save)
                .map(ClienteMapper.fromEntityToClientResponse)
                .orElseThrow(() -> new EntityNotFoundException("Cliente","id",clienteId));
    }

    @Override
    public void delete(Long clienteId) {
        final Cliente cliente = repository.findById(clienteId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Cliente", "id", clienteId));
//        cliente.setEsActivo(Boolean.FALSE);
        repository.save(cliente);
    }

    private boolean isBlanck(String dato) {
        return dato == null || dato.trim().isEmpty();
    }

    private int queryfilterTexto(String texto) {
        return this.isBlanck(texto) ? -1 : 0;
    }

    private String filterTextoQueryUpperLike(String texto) {
        return this.isBlanck(texto) ? "" : "%" + texto.trim().toUpperCase() + "%";
    }
}
