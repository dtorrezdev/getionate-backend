package com.dtorrez.main.modulos.producto.services.presentacion.list;

import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion.list.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IListProductoPresentacionService {

    Page<ListPresentacionResponse> execute(ListPresentacionRequest params, Pageable page);
}
