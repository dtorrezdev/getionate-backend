package bo.com.micrium.modulobase.modulos.ventas.services.venta.get;

import bo.com.micrium.modulobase.common.providers.CurrentUserProvider;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.get.GetVentaRecienteResponse;
import com.micrium.bd.access.jpa.modulo.venta.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetVentaRecienteImp implements IGetVentaRecienteService {

    @Autowired
    private CurrentUserProvider currentUserProvider;

    @Autowired
    private IVentaRepository repository;

    @Override
    public List<GetVentaRecienteResponse> execute() {
        final Long tenantId = currentUserProvider.getUserTenantId();

        return repository.getVentasRecientes(tenantId)
                .stream()
                .map( ele -> new GetVentaRecienteResponse(
                        ele.getImagen(),
                        ele.getProducto(),
                        ele.getSubtotal(),
                        ele.getVentaId())
                )
                .toList();

    }
}
