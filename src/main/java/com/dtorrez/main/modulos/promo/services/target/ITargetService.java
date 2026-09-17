package com.dtorrez.main.modulos.promo.services.target;

import com.dtorrez.main.modulos.promo.controllers.dtos.target.TargetListRequest;
import com.dtorrez.main.modulos.promo.controllers.dtos.target.TargetListResponse;
import com.dtorrez.main.modulos.promo.controllers.dtos.target.TargetRequest;
import com.dtorrez.main.modulos.promo.controllers.dtos.target.TargetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITargetService {

    Page<TargetListResponse> list(TargetListRequest params, Pageable pageable);
    TargetResponse create(TargetRequest request);
    TargetResponse update(TargetRequest request, Long id);
    void delete(String id);

}
