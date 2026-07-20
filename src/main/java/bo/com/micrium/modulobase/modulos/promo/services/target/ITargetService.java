package bo.com.micrium.modulobase.modulos.promo.services.target;

import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.target.TargetListRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.target.TargetListResponse;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.target.TargetRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.target.TargetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITargetService {

    Page<TargetListResponse> list(TargetListRequest params, Pageable pageable);
    TargetResponse create(TargetRequest request);
    TargetResponse update(TargetRequest request, Long id);
    void delete(String id);

}
