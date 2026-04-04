package bo.com.micrium.modulobase.modulos.ventas.services.venta.anular;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.AnularVentaRequest;
import com.micrium.bd.access.jpa.modulo.venta.models.Venta;
import com.micrium.bd.access.jpa.modulo.venta.repository.IClienteRepository;
import com.micrium.bd.access.jpa.modulo.venta.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnularVentaServiceImpl implements IAnularVentaService {

    @Autowired
    private IVentaRepository ventaRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    // private final Logger log = LogManager.getLogger(AnularVentaServiceImpl.class);

    @Override
    public void execute(AnularVentaRequest request) {

        Venta venta = ventaRepository.findById(request.getVentaId())
                .orElseThrow(() -> new RuntimeException("Venta no existe"));

        clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no existe"));

        venta.setGlosa(request.getGlosa());
        venta.setEstado("ANULADO");

        // Modululo de inventari
        // generar un movimiento de entrada
        // aumentar stock del producto (Por devoulcion)

        ventaRepository.save(venta);
    }
}
