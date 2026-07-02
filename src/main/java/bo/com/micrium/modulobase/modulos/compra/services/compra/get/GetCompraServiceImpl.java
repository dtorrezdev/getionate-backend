package bo.com.micrium.modulobase.modulos.compra.services.compra.get;

import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.modulos.compra.Mappers.OrdenCompraMapper;
import bo.com.micrium.modulobase.modulos.compra.Mappers.SolicitudCompraMapper;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.solicitud.GetSolicitudCompraResponse;
import com.micrium.bd.access.jpa.modulo.compra.models.Compra;
import com.micrium.bd.access.jpa.modulo.compra.repositories.ICompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.get.GetOrdenCompraResponse;

@Service
public class GetCompraServiceImpl implements IGetCompraService {

    @Autowired
    private ICompraRepository repository;

    @Override
    public GetOrdenCompraResponse getOrden(Long compraId) {

        final Compra compra = repository.findById(compraId)
                .orElseThrow( ()-> new EntityNotFoundException("OrdenCompra", "id", compraId));

        return OrdenCompraMapper.fromEntityToGetCompraResponse.apply(compra);
    }

    @Override
    public GetSolicitudCompraResponse getSolicitud(Long compraId) {
        final Compra compra = repository.findById(compraId)
                .orElseThrow( ()-> new EntityNotFoundException("SolicitudCompra", "id", compraId));

        return SolicitudCompraMapper.fromEntityToSolicitudResponse.apply(compra);
    }
}

