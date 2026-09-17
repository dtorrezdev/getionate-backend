package com.dtorrez.main.modulos.promo.mappers;

import com.dtorrez.main.modulos.promo.controllers.dtos.promocion.PromocionRequest;
import com.dtorrez.main.modulos.promo.controllers.dtos.promocion.PromocionResponse;
import com.micrium.bd.access.jpa.modulo.promo.models.Promocion;

import java.util.function.Function;

public class PromocionMapper {

    public static final Function<PromocionRequest, Promocion> toEntity = req ->
            Promocion.builder()
                    .nombre(req.getNombre())
                    .descripcion(req.getDescripcion())
                    .fechaInicio(req.getFechaInicio())
                    .fechaFin(req.getFechaFin())
                    .limiteUso(req.getLimiteUso())
                    .limitePorCliente(req.getLimitePorCliente())
                    .isActive(Boolean.TRUE)
                    .build();

    public static final Function<Promocion, PromocionResponse> toResponse =
            entity -> new PromocionResponse(
                    entity.getId(),
                    entity.getNombre(),
                    entity.getDescripcion(),
                    entity.getFechaInicio(),
                    entity.getFechaFin(),
                    entity.getIsActive(),
                    entity.getLimiteUso(),
                    entity.getLimitePorCliente()
            );
}
