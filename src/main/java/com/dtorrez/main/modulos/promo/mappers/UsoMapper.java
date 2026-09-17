package com.dtorrez.main.modulos.promo.mappers;

import com.dtorrez.main.modulos.promo.controllers.dtos.uso.UsoRequest;
import com.dtorrez.main.modulos.promo.controllers.dtos.uso.UsoResponse;
import com.micrium.bd.access.jpa.modulo.promo.models.Promocion;
import com.micrium.bd.access.jpa.modulo.promo.models.Uso;

import java.util.function.Function;

public class UsoMapper {

    public static final Function<UsoRequest, Uso> toEntity = req ->
            Uso.builder()
                    .promocionId(req.getPromocionId())
                    .clienteId(req.getClienteId())
                    .ventaId(req.getVentaId())
                    .cantidadDescuento(req.getCantidadDescuento())
                    .build();

    public static final Function<Uso, UsoResponse> toResponse =
            entity -> new UsoResponse(
                    entity.getId(),
                    entity.getPromocionId(),
                    entity.getClienteId(),
                    entity.getVentaId(),
                    entity.getCantidadDescuento()
            );
}
