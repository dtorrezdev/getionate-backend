package com.dtorrez.main.modulos.compra.services.proveedor;

import com.dtorrez.main.modulos.compra.controllers.dtos.proveedor.ProveedorRequest;
import com.dtorrez.main.modulos.compra.controllers.dtos.proveedor.ProveedorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IProveedorService {

    Page<ProveedorResponse> list(ProveedorRequest params, Pageable pageable);
    ProveedorResponse create(ProveedorRequest request);
    ProveedorResponse update(ProveedorRequest request, Long id);
    void delete(String id);
}

