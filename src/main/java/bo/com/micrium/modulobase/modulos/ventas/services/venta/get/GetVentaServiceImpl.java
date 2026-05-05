package bo.com.micrium.modulobase.modulos.ventas.services.venta.get;

import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.VentaResponse;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.get.GetVentaResponse;
import bo.com.micrium.modulobase.modulos.ventas.mapper.VentaMapper;
import com.micrium.bd.access.jpa.modulo.venta.models.Venta;
import com.micrium.bd.access.jpa.modulo.venta.repository.IVentaRepository;
import jakarta.persistence.OneToMany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetVentaServiceImpl implements IGetVentaService {

    @Autowired
    private IVentaRepository repository;

    @Override
    public GetVentaResponse execute(Long ventaId) {
        final Venta venta =repository.findById(ventaId)
                .orElseThrow( ()-> new EntityNotFoundException("Venta","id",ventaId));
        return VentaMapper.entityToGetResponse.apply(venta);
    }
}
