package bo.com.micrium.modulobase.modulos.promo.services.beneficio;

import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.beneficio.BeneficioRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.beneficio.BeneficioResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBeneficioService {

    Page<BeneficioResponse> list(BeneficioRequest params, Pageable pageable);
    BeneficioResponse create(BeneficioRequest request);
    BeneficioResponse update(BeneficioRequest request, Long id);
    void delete(String id);

}
