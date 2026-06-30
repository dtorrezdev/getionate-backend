package bo.com.micrium.modulobase.modulos.compra.services.compra.create;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.crear.CompraRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.orden_compra.crear.CompraResponse;

public interface ICreateCompraService {

    CompraResponse createOrden(CompraRequest request);

    CompraResponse createSolicitud(CompraRequest request);

}
