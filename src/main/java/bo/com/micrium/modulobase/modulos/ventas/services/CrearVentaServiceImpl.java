package bo.com.micrium.modulobase.modulos.ventas.services;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.VentaRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.VentaResponse;
import com.micrium.bd.access.jpa.modulo.venta.models.Venta;
import com.micrium.bd.access.jpa.modulo.venta.repository.IDetalleVenta;
import com.micrium.bd.access.jpa.modulo.venta.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CrearVentaServiceImpl implements ICrearVentaService {

    @Autowired
    private IVentaRepository repository;

    @Autowired
    private IDetalleVenta detalleRepository;

    @Override
    public VentaResponse execute(VentaRequest crearVentaRequest) {

//        final Venta newVenta = Mapper.


        return null;
    }
}
