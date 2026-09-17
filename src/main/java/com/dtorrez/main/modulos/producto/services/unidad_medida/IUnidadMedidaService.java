package com.dtorrez.main.modulos.producto.services.unidad_medida;

import com.dtorrez.main.modulos.producto.controllers.dtos.unidad_medida.UnidadMedidaRequest;
import com.dtorrez.main.modulos.producto.controllers.dtos.unidad_medida.UnidadMedidaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUnidadMedidaService {

    Page<UnidadMedidaResponse> list(UnidadMedidaRequest params, Pageable pageRequest);

    UnidadMedidaResponse create(UnidadMedidaRequest request);

    UnidadMedidaResponse update(UnidadMedidaRequest request, Long id);

    void delete(Long id);
}
