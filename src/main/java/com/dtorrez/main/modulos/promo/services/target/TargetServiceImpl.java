package com.dtorrez.main.modulos.promo.services.target;

import com.dtorrez.main.common.exceptions.EntityNotFoundException;
import com.dtorrez.main.modulos.promo.controllers.dtos.target.TargetListRequest;
import com.dtorrez.main.modulos.promo.controllers.dtos.target.TargetListResponse;
import com.dtorrez.main.modulos.promo.controllers.dtos.target.TargetRequest;
import com.dtorrez.main.modulos.promo.controllers.dtos.target.TargetResponse;
import com.dtorrez.main.modulos.promo.mappers.TargetMapper;
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
    public Page<TargetListResponse> list(TargetListRequest params, Pageable pageable) {

        return repository.filter(
                queryfilterTexto(params.getPromocionId()),
                filterTextoQueryUpperLike(params.getPromocionId()),
                queryfilterTexto(params.getPresentacionId()),
                filterTextoQueryUpperLike(params.getPresentacionId()),
                queryfilterTexto(params.getCategoriaId()),
                filterTextoQueryUpperLike(params.getCategoriaId()),
                queryfilterTexto(params.getMarcaId()),
                filterTextoQueryUpperLike(params.getMarcaId()),
                pageable).map(TargetMapper.toResponseList);
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

    private boolean isBlanck(String dato) {
        return dato == null || dato.trim().isEmpty();
    }

    private int queryfilterTexto(String texto) {
        return this.isBlanck(texto) ? -1 : 0;
    }

    private String filterTextoQueryUpperLike(String texto) {
        return this.isBlanck(texto) ? "" : "%" + texto.trim().toUpperCase() + "%";
    }

    private String filterTextoQueryUpper(String texto) {
        return this.isBlanck(texto) ? "" : texto.trim().toUpperCase();
    }
}
