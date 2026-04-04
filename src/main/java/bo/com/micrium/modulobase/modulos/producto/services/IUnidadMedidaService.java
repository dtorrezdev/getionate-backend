package bo.com.micrium.modulobase.modulos.producto.services;

import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.unidad_medida.UnidadMedidaRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.unidad_medida.UnidadMedidaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface IUnidadMedidaService {

    Page<UnidadMedidaResponse> list(Map<String, String> params, Pageable pageRequest);

    UnidadMedidaResponse create(UnidadMedidaRequest marcaRequest);

    UnidadMedidaResponse update(UnidadMedidaRequest marcaRequest, String id);

    void delete(String id);
}
