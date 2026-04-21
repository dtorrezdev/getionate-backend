package bo.com.micrium.modulobase.modulos.producto.services.marca;

import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.marca.MarcaRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.marca.MarcaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface IMarcaService {

    Page<MarcaResponse> list(Map<String, String> params, Pageable pageRequest);

    MarcaResponse create(MarcaRequest marcaRequest);

    MarcaResponse update(MarcaRequest marcaRequest, String id);

    void delete(String id);
}
