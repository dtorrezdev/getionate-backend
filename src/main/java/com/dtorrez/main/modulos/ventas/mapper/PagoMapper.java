package com.dtorrez.main.modulos.ventas.mapper;

import com.dtorrez.main.modulos.ventas.controllers.dtos.pago.DetallePagoResponse;
import com.dtorrez.main.modulos.ventas.controllers.dtos.pago.PagoRequest;
import com.dtorrez.main.modulos.ventas.controllers.dtos.pago.PagoResponse;
import com.micrium.bd.access.jpa.modulo.venta.models.Pago;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PagoMapper {

    private PagoMapper() { throw new AssertionError(); }

    public static final Function<PagoRequest, List<Pago>>
            toEntities = request -> request.getDetallePago()
                .stream()
                .map(detalle -> {
                    return Pago.builder()
                            .tipoPago(detalle.getTipo())
                            .total(detalle.getMonto())
                            //.ventaId(request.getVentaId())
                            .build();
                })
                .collect(Collectors.toList());

    public static final Function<List<Pago>, PagoResponse>
            toListResponse = entities -> {
            PagoResponse response = new PagoResponse();
            final List<DetallePagoResponse> collect = entities
                    .stream()
                    .map(pago -> DetallePagoResponse
                                .builder()
                                .id(pago.getId())
                                .tipoPago(pago.getTipoPago())
                                .total(pago.getTotal())
                                //.ventaId(pago.getVentaId())
                                .build()
                    )
                    .collect(Collectors.toList());
        response.setPagos(collect);
        return response;
    };
}
