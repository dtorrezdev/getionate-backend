package bo.com.micrium.modulobase.modulos.producto.services.unidad_medida;

import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.unidad_medida.UnidadMedidaRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.unidad_medida.UnidadMedidaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface IUnidadMedidaService {

    Page<UnidadMedidaResponse> list(UnidadMedidaRequest params, Pageable pageRequest);

    UnidadMedidaResponse create(UnidadMedidaRequest request);

    UnidadMedidaResponse update(UnidadMedidaRequest request, Long id);

    void delete(Long id);
}
