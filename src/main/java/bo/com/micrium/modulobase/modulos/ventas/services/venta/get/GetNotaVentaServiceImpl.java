package bo.com.micrium.modulobase.modulos.ventas.services.venta.get;

import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.common.providers.CurrentUserProvider;
import bo.com.micrium.modulobase.modulos.administracion.services.tenant.ITenantService;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.get.GetNotaVentaResponse;
import bo.com.micrium.modulobase.modulos.ventas.mapper.VentaMapper;
import com.micrium.bd.access.jpa.modulo.venta.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetNotaVentaServiceImpl implements IGetNotaVentaService {
    @Autowired
    private ITenantService tenantService;

    @Autowired
    private IVentaRepository repository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

    @Override
    public GetNotaVentaResponse execute(Long ventaId) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        var tenant = tenantService.get(tenantId);

        final var headerVventa = repository.getHeaderVentaByid(ventaId, tenantId)
                .orElseThrow( ()-> new EntityNotFoundException("Venta","id",ventaId));

        var header = VentaMapper.toHeaderVenta.apply(headerVventa);
        var detalle = VentaMapper
                .toDetalleVenta
                .apply(repository.getDetalleVentaByid(ventaId, tenantId));

        return GetNotaVentaResponse.builder()
                .tenant(tenant)
                .header(header)
                .detalle(detalle)
                .build();
    }
}
