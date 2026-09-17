package com.dtorrez.main.modulos.promo.services.beneficio;

import com.dtorrez.main.modulos.promo.controllers.dtos.beneficio.BeneficioListRequest;
import com.dtorrez.main.modulos.promo.controllers.dtos.beneficio.BeneficioListResponse;
import com.dtorrez.main.modulos.promo.controllers.dtos.beneficio.BeneficioRequest;
import com.dtorrez.main.modulos.promo.controllers.dtos.beneficio.BeneficioResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBeneficioService {

    Page<BeneficioListResponse> list(BeneficioListRequest params, Pageable pageable);
    BeneficioResponse create(BeneficioRequest request);
    BeneficioResponse update(BeneficioRequest request, Long id);
    void delete(String id);

}
