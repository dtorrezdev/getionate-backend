package bo.com.micrium.modulobase.modulos.promo.services.promocion;

import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.promocion.PromocionListRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.promocion.PromocionRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.promocion.PromocionResponse;
import bo.com.micrium.modulobase.modulos.promo.mappers.PromocionMapper;
import com.micrium.bd.access.jpa.modulo.promo.repository.IPromocionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PromocionServiceImpl implements IPromocionService {

    @Autowired
    private IPromocionRepository repository;

    @Override
    public Page<PromocionResponse> list(PromocionListRequest params, Pageable pageable) {

        return repository.filter(
                queryfilterTexto(params.getNombre()),
                filterTextoQueryUpperLike(params.getNombre()),
                queryfilterTexto(params.getDescripcion()),
                filterTextoQueryUpperLike(params.getDescripcion()),
                queryfilterTexto(params.getFechaInicio()),
                filterTextoQueryUpperLike(params.getFechaInicio()),
                queryfilterTexto(params.getFechaFin()),
                filterTextoQueryUpperLike(params.getFechaFin()),
                queryfilterTexto(params.getLimiteUso()),
                filterTextoQueryUpperLike(params.getLimiteUso()),
                queryfilterTexto(params.getLimitePorCliente()),
                filterTextoQueryUpperLike(params.getLimitePorCliente()),
                pageable
        ).map(PromocionMapper.toResponse);
    }

    @Override
    public PromocionResponse create(PromocionRequest request) {
        final var promocion = PromocionMapper.toEntity.apply(request);

        return PromocionMapper.toResponse
                .apply(repository.save(promocion));
    }

    @Override
    public PromocionResponse update(PromocionRequest request, Long id) {

        return repository.findById(id)
                .map(promocion -> {
                    promocion.setNombre(request.getNombre());
                    promocion.setDescripcion(request.getDescripcion());
                    promocion.setFechaInicio(request.getFechaInicio());
                    promocion.setFechaFin(request.getFechaFin());
                    promocion.setIsActive(request.getIsActive());
                    promocion.setLimiteUso(request.getLimiteUso());
                    promocion.setLimitePorCliente(request.getLimitePorCliente());
                    return promocion;
                })
                .map(repository::save)
                .map(PromocionMapper.toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Promocion","id", id));
    }

    @Override
    public void delete(String id) {
        final var promocion = repository.findById(Long.valueOf(id))
                .orElseThrow(() ->
                        new EntityNotFoundException("Promocion","id", id));
        promocion.setIsActive(Boolean.FALSE);
        repository.save(promocion);
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
