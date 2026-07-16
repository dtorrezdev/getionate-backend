package bo.com.micrium.modulobase.modulos.promo.services.uso;

import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.uso.UsoListRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.uso.UsoRequest;
import bo.com.micrium.modulobase.modulos.promo.controllers.dtos.uso.UsoResponse;
import bo.com.micrium.modulobase.modulos.promo.mappers.UsoMapper;
import com.micrium.bd.access.jpa.modulo.promo.repository.IUsoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsoServiceImpl implements IUsoService {

    @Autowired
    private IUsoRepository repository;

    @Override
    public Page<UsoResponse> list(UsoListRequest params, Pageable pageable) {

        return repository.filter(
                queryfilterTexto(params.getPromocionId()),
                filterTextoQueryUpperLike(params.getPromocionId()),
                queryfilterTexto(params.getClienteId()),
                filterTextoQueryUpperLike(params.getClienteId()),
                queryfilterTexto(params.getVentaId()),
                filterTextoQueryUpperLike(params.getVentaId()),
                pageable
        ).map(UsoMapper.toResponse);
    }

    @Override
    public UsoResponse create(UsoRequest request) {
        final var promocionUso = UsoMapper.toEntity.apply(request);

        return UsoMapper.toResponse
                .apply(repository.save(promocionUso));
    }

    @Override
    public UsoResponse update(UsoRequest request, Long id) {

        return repository.findById(id)
                .map(uso -> {
                    uso.setPromocionId(request.getPromocionId());
                    uso.setClienteId(request.getClienteId());
                    uso.setVentaId(request.getVentaId());
                    uso.setCantidadDescuento(request.getCantidadDescuento());
                    return uso;
                })
                .map(repository::save)
                .map(UsoMapper.toResponse)
                .orElseThrow(() -> new EntityNotFoundException("PromocionUso","id", id));
    }

    @Override
    public void delete(String id) {
        // TODO: implements method
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
