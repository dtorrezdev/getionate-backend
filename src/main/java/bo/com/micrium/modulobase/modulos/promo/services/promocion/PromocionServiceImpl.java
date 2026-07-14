package bo.com.micrium.modulobase.modulos.promo.services.promocion;

import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.promocion.PromocionRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.promocion.PromocionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class PromocionServiceImpl implements IPromocionService {

    @Override
    public Page<PromocionResponse> list(PromocionRequest params, Pageable pageable) {
        // TODO: implementar metodo list
        return null;
    }

    @Override
    public PromocionResponse create(PromocionRequest request) {
        // TODO: implementar metodo create
        return new PromocionResponse();
    }

    @Override
    public PromocionResponse update(PromocionRequest request, Long id) {
        // TODO: implementar metodo update
        return new PromocionResponse();
    }

    @Override
    public void delete(String id) {

    }
}
