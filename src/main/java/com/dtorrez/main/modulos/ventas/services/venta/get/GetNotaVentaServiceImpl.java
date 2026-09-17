package com.dtorrez.main.modulos.ventas.services.venta.get;

import com.dtorrez.main.common.exceptions.EntityNotFoundException;
import com.dtorrez.main.common.providers.CurrentUserProvider;
import com.dtorrez.main.modulos.administracion.services.tenant.ITenantService;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.get.GetNotaVentaResponse;
import com.dtorrez.main.modulos.ventas.mapper.VentaMapper;
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
