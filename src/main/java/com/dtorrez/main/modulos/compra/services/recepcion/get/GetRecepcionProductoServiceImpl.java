package com.dtorrez.main.modulos.compra.services.recepcion.get;

import com.dtorrez.main.common.exceptions.EntityNotFoundException;
import com.dtorrez.main.modulos.compra.Mappers.RecepcionProductoMapper;
import com.micrium.bd.access.jpa.modulo.compra.models.Compra;
import com.micrium.bd.access.jpa.modulo.compra.repositories.IRecepcionProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dtorrez.main.modulos.compra.controllers.dtos.recepcion.get.GetRecepcionProductoResponse;

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

