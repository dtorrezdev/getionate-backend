package bo.com.micrium.modulobase.modulos.compra.Mappers;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.proveedor.ProveedorRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.proveedor.ProveedorResponse;
import com.micrium.bd.access.jpa.modulo.compra.models.Proveedor;

import java.util.function.Function;

public class ProveedorMapper {

    public static final Function<ProveedorRequest, Proveedor> toEntity = req ->
            Proveedor.builder()
                    .nombre(req.getNombre())
                    .descripcion(req.getDescripcion())
                    .esActivo(Boolean.TRUE)
                    .build();

    public static final Function<Proveedor, ProveedorResponse> toResponse =
            m -> new ProveedorResponse(m.getId(), m.getNombre(), m.getDescripcion());
}
