package bo.com.micrium.modulobase.modulos.compra.services.recepcion.get;

import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.modulos.compra.Mappers.RecepcionProductoMapper;
import com.micrium.bd.access.jpa.modulo.compra.models.Compra;
import com.micrium.bd.access.jpa.modulo.compra.repositories.IRecepcionProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.recepcion.get.GetRecepcionProductoResponse;

@Service
public class GetRecepcionProductoServiceImpl implements IGetRecepcionProductoService {

    @Autowired
    private IRecepcionProductoRepository repository;

    @Override
    public GetRecepcionProductoResponse execute(Long recepcionProductoId) {

        final var recepcion = repository.findById(recepcionProductoId)
                .orElseThrow( ()-> new EntityNotFoundException("RecepcionProducto", "id", recepcionProductoId));

        return RecepcionProductoMapper.fromEntityToGetRecepcionResponse.apply(recepcion);
    }
}

