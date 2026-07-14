package bo.com.micrium.modulobase.modulos.promo.services.promocion;

import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.promocion.PromocionRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.promocion.PromocionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPromocionService {

    Page<PromocionResponse> list(PromocionRequest params, Pageable pageable);
    PromocionResponse create(PromocionRequest request);
    PromocionResponse update(PromocionRequest request, Long id);
    void delete(String id);

}
