package bo.com.micrium.modulobase.modulos.promo.services.uso;

import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.uso.UsoRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.uso.UsoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsoServiceImpl implements IUsoService {

    @Override
    public Page<UsoResponse> list(UsoRequest params, Pageable pageable) {
        // TODO: implements method
        return null;
    }

    @Override
    public UsoResponse create(UsoRequest request) {
        // TODO: implements method
        return null;
    }

    @Override
    public UsoResponse update(UsoRequest request, Long id) {
        // TODO: implements method
        return null;
    }

    @Override
    public void delete(String id) {
        // TODO: implements method
    }
}
