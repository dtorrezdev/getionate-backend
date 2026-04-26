package bo.com.micrium.modulobase.modulos.compra.services.compra.anular;

import bo.com.micrium.modulobase.modulos.compra.controllers.dtos.compra.AnularCompraRequest;

public interface IAnularCompraService {

    void execute(AnularCompraRequest request);
}

