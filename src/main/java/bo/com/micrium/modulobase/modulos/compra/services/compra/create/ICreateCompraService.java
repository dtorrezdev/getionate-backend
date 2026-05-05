package bo.com.micrium.modulobase.modulos.compra.services.compra.create;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.crear.CompraRequest;
import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.crear.CompraResponse;

public interface ICreateCompraService {

    CompraResponse execute(CompraRequest request);

}
