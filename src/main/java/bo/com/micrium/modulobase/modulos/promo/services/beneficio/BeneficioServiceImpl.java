package bo.com.micrium.modulobase.modulos.promo.services.beneficio;

import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.beneficio.BeneficioRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.beneficio.BeneficioResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class BeneficioServiceImpl implements IBeneficioService {
    @Override
    public Page<BeneficioResponse> list(BeneficioRequest params, Pageable pageable) {
        // TODO: implementar method
        return null;
    }

    @Override
    public BeneficioResponse create(BeneficioRequest request) {
        // TODO: implementar method
        return null;
    }

    @Override
    public BeneficioResponse update(BeneficioRequest request, Long id) {
        // TODO: implementar method
        return null;
    }

    @Override
    public void delete(String id) {
        // TODO: implementar method
    }
}
