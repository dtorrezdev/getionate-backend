package bo.com.micrium.modulobase.modulos.promo.mappers;

import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.beneficio.BeneficioRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.promocion.PromocionRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.promocion.PromocionResponse;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.target.TargetRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.target.TargetResponse;
import com.micrium.bd.access.jpa.modulo.promo.models.Promocion;
import com.micrium.bd.access.jpa.modulo.promo.models.Target;

import java.util.function.Function;

public class TargetMapper {

    public static final Function<TargetRequest, Target> toEntity = req ->
            Target.builder()
                    .promocionId(req.getPromocionId())
                    .presentacionId(req.getPresentacionId())
                    .categoriaId(req.getCategoriaId())
                    .marcaId(req.getMarcaId())
                    .build();

    public static final Function<Target, TargetResponse> toResponse =
            entity -> new TargetResponse(
                    entity.getId(),
                    entity.getPromocionId(),
                    entity.getPresentacionId(),
                    entity.getCategoriaId(),
                    entity.getMarcaId()
            );
}
