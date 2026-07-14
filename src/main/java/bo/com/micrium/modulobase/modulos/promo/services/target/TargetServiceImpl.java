package bo.com.micrium.modulobase.modulos.promo.services.target;

import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.target.TargetRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.target.TargetResponse;
import bo.com.micrium.modulobase.modulos.promo.mappers.PromocionMapper;
import bo.com.micrium.modulobase.modulos.promo.mappers.TargetMapper;
import com.micrium.bd.access.jpa.modulo.promo.repository.ITargetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TargetServiceImpl implements ITargetService {

    @Autowired
    private ITargetRepository repository;

    @Override
    public Page<TargetResponse> list(TargetRequest params, Pageable pageable) {
        // TODO: implement method
        return null;
    }

    @Override
    public TargetResponse create(TargetRequest request) {
        final var target = TargetMapper.toEntity.apply(request);

        return TargetMapper.toResponse
                .apply(repository.save(target));
    }

    @Override
    public TargetResponse update(TargetRequest request, Long id) {

        return repository.findById(id)
                .map(target -> {
                    target.setPromocionId(request.getPromocionId());
                    target.setPresentacionId(request.getPresentacionId());
                    target.setCategoriaId(request.getCategoriaId());
                    target.setMarcaId(request.getMarcaId());
                    return target;
                })
                .map(repository::save)
                .map(TargetMapper.toResponse)
                .orElseThrow(() -> new EntityNotFoundException("PromocionTarget","id", id));
    }

    @Override
    public void delete(String id) {
        // TODO: implement method
    }
}
