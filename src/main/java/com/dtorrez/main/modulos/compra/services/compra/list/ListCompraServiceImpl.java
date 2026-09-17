package com.dtorrez.main.modulos.compra.services.compra.list;

import com.dtorrez.main.common.providers.CurrentUserProvider;
import com.dtorrez.main.modulos.compra.Mappers.OrdenCompraMapper;
import com.dtorrez.main.modulos.compra.Mappers.SolicitudCompraMapper;
import com.dtorrez.main.modulos.compra.controllers.dtos.solicitud.SolicitudCompraRequest;
import com.dtorrez.main.modulos.compra.controllers.dtos.solicitud.SolicitudCompraResponse;
import com.micrium.bd.access.jpa.modulo.compra.repositories.ICompraRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.dtorrez.main.modulos.compra.controllers.dtos.orden_compra.list.OrdenCompraRequest;
import com.dtorrez.main.modulos.compra.controllers.dtos.orden_compra.list.OrdenCompraResponse;

@Service
public class ListCompraServiceImpl implements IListCompraService {

    @Autowired
    private ICompraRepository repository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

    private final Logger log = LogManager.getLogger(ListCompraServiceImpl.class);

    @Override
    public Page<OrdenCompraResponse> listOrdenCompra(OrdenCompraRequest request, Pageable page) {
        log.info("params: " + request);
        log.info("page: " + page);
        long tenantId = currentUserProvider.getUserTenantId();

        return repository.filterOrdenCompra(
                        queryfilterTexto(request.getCodigo()),
                        filterTextoQueryUpperLike(request.getCodigo()),
                        queryfilterTexto(request.getGlosa()),
                        filterTextoQueryUpperLike(request.getGlosa()),
                        queryfilterTexto(request.getFecha()),
                        filterTextoQueryUpperLike(request.getFecha()),
                        queryfilterTexto(request.getProvedor()),
                        filterTextoQueryUpperLike(request.getProvedor()),
                        queryfilterTexto(request.getEstado()),
                        filterTextoQueryUpper(request.getEstado()),
                        queryfilterTexto(request.getTipoCompra()),
                        filterTextoQueryUpper(request.getTipoCompra()),
                        queryfilterTexto(request.getSolicitante()),
                        filterTextoQueryUpper(request.getSolicitante()),
                        queryfilterTexto(request.getAprobador()),
                        filterTextoQueryUpper(request.getAprobador()),
                        page, tenantId)
                .map(OrdenCompraMapper.fromProjectionToListCompraResponse);
    }

    @Override
    public Page<SolicitudCompraResponse> listSolicitudCompra(SolicitudCompraRequest request, Pageable page) {
        // fromProjectionToSolicitudCompraResponse
        long tenantId = currentUserProvider.getUserTenantId();
        log.info("params: " + request);
        log.info("page: " + page);

        return repository.filterSolicitud(
                        queryfilterTexto(request.getCodigo()),
                        filterTextoQueryUpperLike(request.getCodigo()),
                        queryfilterTexto(request.getGlosa()),
                        filterTextoQueryUpperLike(request.getGlosa()),
                        queryfilterTexto(request.getFecha()),
                        filterTextoQueryUpperLike(request.getFecha()),
                        queryfilterTexto(request.getEstado()),
                        filterTextoQueryUpper(request.getEstado()),
                        queryfilterTexto(request.getSolicitante()),
                        filterTextoQueryUpper(request.getSolicitante()),
                        queryfilterTexto(request.getAprobador()),
                        filterTextoQueryUpper(request.getAprobador()),
                        page, tenantId)
                .map(SolicitudCompraMapper.fromProjectionToSolicitudCompraResponse);
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

