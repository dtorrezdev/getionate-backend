package com.dtorrez.main.modulos.administracion.mappers;

import com.dtorrez.main.modulos.administracion.controllers.dtos.tenant.TenantRequest;
import com.dtorrez.main.modulos.administracion.controllers.dtos.tenant.TenantResponse;
import com.dtorrez.main.modulos.administracion.controllers.dtos.tenant.ThemeRequest;
import com.micrium.bd.access.jpa.modulo.administracion.models.Tenant;
import com.micrium.bd.access.jpa.modulo.administracion.models.Theme;

import java.util.function.Function;

public class TenantMapper {

    public static final Function<TenantRequest, Tenant> toEntity =
            req -> {
                var tenant = Tenant.builder()
                        .nombre(req.getNombre())
                        .logoUrl(req.getLogoUrl())
                        .direccion(req.getDireccion())
                        .celular(req.getCelular())
                        .ciudad(req.getCiudad())
                        .esActivo(Boolean.TRUE)
                        .build();

            var theme = req.getTheme() != null ?
                    Theme.builder()
                    .darkMode(req.getTheme().getDarkMode())
                    .font(req.getTheme().getFont())
                    .primaryColor(req.getTheme().getPrimaryColor())
                    .logo(req.getTheme().getLogo())
                    .surfaceStyle(req.getTheme().getSurfaceStyle())
                    .tenant(tenant)
                    .build(): null;
                tenant.setTheme(theme);
                return tenant;

    };
    public static final Function<Tenant, TenantResponse> toResponse =
            t -> new TenantResponse(
                    t.getId(),
                    t.getNombre(),
                    t.getLogoUrl(),
                    t.getDireccion(),
                    t.getCelular(),
                    t.getCiudad(),
                    TenantMapper.toThemeResponse.apply(t.getTheme())
            );

    public static final Function<Theme, ThemeRequest> toThemeResponse =
            t -> {
                if (t == null) return null;
                return new ThemeRequest(
                        t.getPrimaryColor(),
                        t.getSurfaceStyle(),
                        t.getDarkMode(),
                        t.getLogo(),
                        t.getFont()
                );
            };
}

