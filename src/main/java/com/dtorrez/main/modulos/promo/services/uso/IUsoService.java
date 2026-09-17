package com.dtorrez.main.modulos.promo.services.uso;

import com.dtorrez.main.modulos.promo.controllers.dtos.uso.UsoListRequest;
import com.dtorrez.main.modulos.promo.controllers.dtos.uso.UsoRequest;
import com.dtorrez.main.modulos.promo.controllers.dtos.uso.UsoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUsoService {
    Page<UsoResponse> list(UsoListRequest params, Pageable pageable);
    UsoResponse create(UsoRequest request);
    UsoResponse update(UsoRequest request, Long id);
    void delete(String id);
}
