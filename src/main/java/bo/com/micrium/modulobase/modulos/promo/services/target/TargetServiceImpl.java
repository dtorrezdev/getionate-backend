package bo.com.micrium.modulobase.modulos.promo.services.target;

import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.target.TargetRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.target.TargetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class TargetServiceImpl implements ITargetService {

    @Override
    public Page<TargetResponse> list(TargetRequest params, Pageable pageable) {
        // TODO: implement method
        return null;
    }

    @Override
    public TargetResponse create(TargetRequest request) {
        // TODO: implement method
        return null;
    }

    @Override
    public TargetResponse update(TargetRequest request, Long id) {
        // TODO: implement method
        return null;
    }

    @Override
    public void delete(String id) {
        // TODO: implement method
    }
}
