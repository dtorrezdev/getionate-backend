package bo.com.micrium.modulobase.modulos.administracion.mappers;

import bo.com.micrium.modulobase.modulos.administracion.controllers.dtos.tenant.TenantRequest;
import bo.com.micrium.modulobase.modulos.administracion.controllers.dtos.tenant.TenantResponse;
import com.micrium.bd.access.jpa.modulo.administracion.models.Tenant;

import java.util.function.Function;

public class TenantMapper {

    public static final Function<TenantRequest, Tenant> toEntity = req ->
            Tenant.builder()
                    .nombre(req.getNombre())
                    .logoUrl(req.getLogoUrl())
                    .direccion(req.getDireccion())
                    .celular(req.getCelular())
                    .ciudad(req.getCiudad())
                    .esActivo(Boolean.TRUE)
                    .build();

    public static final Function<Tenant, TenantResponse> toResponse =
            t -> new TenantResponse(
                    t.getId(),
                    t.getNombre(),
                    t.getLogoUrl(),
                    t.getDireccion(),
                    t.getCelular(),
                    t.getCiudad(),
                    t.getEsActivo()
            );
}

