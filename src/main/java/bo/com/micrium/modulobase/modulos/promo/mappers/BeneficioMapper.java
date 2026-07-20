package bo.com.micrium.modulobase.modulos.promo.mappers;

import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.beneficio.BeneficioListResponse;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.beneficio.BeneficioRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.beneficio.BeneficioResponse;
import com.micrium.bd.access.jpa.modulo.promo.models.Beneficio;
import com.micrium.bd.access.jpa.modulo.promo.projection.BeneficioProjection;

import java.util.function.Function;

public class BeneficioMapper {
    public static final Function<BeneficioRequest, Beneficio> toEntity = req ->
            Beneficio.builder()
                    .promocionId(req.getPromocionId())
                    .tipo(req.getTipo())
                    .valor(req.getValor())
                    .maximoDescuento(req.getMaximoDescuento())
                    .build();

    public static final Function<Beneficio, BeneficioResponse> toResponse =
            entity -> new BeneficioResponse(
                    entity.getId(),
                    entity.getPromocionId(),
                    entity.getTipo(),
                    entity.getValor(),
                    entity.getMaximoDescuento()
            );

    public static final Function<BeneficioProjection, BeneficioListResponse> toResponseList =
            entity -> new BeneficioListResponse(
                    entity.getId(),
                    entity.getPromocionId(),
                    entity.getPromocion(),
                    entity.getTipo(),
                    entity.getValor(),
                    entity.getMaximoDescuento()
            );
}
