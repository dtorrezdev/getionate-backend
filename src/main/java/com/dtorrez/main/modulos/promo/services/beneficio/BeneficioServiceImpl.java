package com.dtorrez.main.modulos.promo.services.beneficio;

import com.dtorrez.main.common.exceptions.EntityNotFoundException;
import com.dtorrez.main.modulos.promo.controllers.dtos.beneficio.BeneficioListRequest;
import com.dtorrez.main.modulos.promo.controllers.dtos.beneficio.BeneficioListResponse;
import com.dtorrez.main.modulos.promo.controllers.dtos.beneficio.BeneficioRequest;
import com.dtorrez.main.modulos.promo.controllers.dtos.beneficio.BeneficioResponse;
import com.dtorrez.main.modulos.promo.mappers.BeneficioMapper;
import com.micrium.bd.access.jpa.modulo.promo.repository.IBeneficioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BeneficioServiceImpl implements IBeneficioService {

    @Autowired
    private IBeneficioRepository repository;

    @Override
    public Page<BeneficioListResponse> list(BeneficioListRequest request, Pageable pageable) {

        return repository.filter(
                queryfilterTexto(request.getPromocionId()),
                filterTextoQueryUpperLike(request.getPromocionId()),
                queryfilterTexto(request.getTipo()),
                filterTextoQueryUpperLike(request.getTipo()),
                pageable).map(BeneficioMapper.toResponseList);
    }

    @Override
    public BeneficioResponse create(BeneficioRequest request) {
        final var beneficio = BeneficioMapper.toEntity.apply(request);

        return BeneficioMapper.toResponse
                .apply(repository.save(beneficio));
    }

    @Override
    public BeneficioResponse update(BeneficioRequest request, Long id) {

        return repository.findById(id)
                .map(beneficio -> {
                    beneficio.setPromocionId(request.getPromocionId());
                    beneficio.setTipo(request.getTipo());
                    beneficio.setValor(request.getValor());
                    beneficio.setMaximoDescuento(request.getMaximoDescuento());
                    return beneficio;
                })
                .map(repository::save)
                .map(BeneficioMapper.toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Beneficio","id", id));
    }

    @Override
    public void delete(String id) {
        // TODO: implementar method
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
}
