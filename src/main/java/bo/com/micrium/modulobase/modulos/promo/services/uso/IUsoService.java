package bo.com.micrium.modulobase.modulos.promo.services.uso;

import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.uso.UsoListRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.uso.UsoRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.uso.UsoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUsoService {
    Page<UsoResponse> list(UsoListRequest params, Pageable pageable);
    UsoResponse create(UsoRequest request);
    UsoResponse update(UsoRequest request, Long id);
    void delete(String id);
}
