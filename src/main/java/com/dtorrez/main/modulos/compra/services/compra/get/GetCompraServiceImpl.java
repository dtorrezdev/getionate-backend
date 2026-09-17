package com.dtorrez.main.modulos.compra.services.compra.get;

import com.dtorrez.main.common.exceptions.EntityNotFoundException;
import com.dtorrez.main.modulos.compra.Mappers.OrdenCompraMapper;
import com.dtorrez.main.modulos.compra.Mappers.SolicitudCompraMapper;
import com.dtorrez.main.modulos.compra.controllers.dtos.solicitud.GetSolicitudCompraResponse;
import com.micrium.bd.access.jpa.modulo.compra.models.Compra;
import com.micrium.bd.access.jpa.modulo.compra.repositories.ICompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dtorrez.main.modulos.compra.controllers.dtos.orden_compra.get.GetOrdenCompraResponse;

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

