package bo.com.micrium.modulobase.modulos.ventas.services.pago;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.pago.PagoRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.pago.PagoResponse;
import bo.com.micrium.modulobase.modulos.ventas.mapper.PagoMapper;
import com.micrium.bd.access.jpa.modulo.venta.models.Pago;
import com.micrium.bd.access.jpa.modulo.venta.repository.IPagoRepository;
import com.micrium.bd.access.jpa.modulo.venta.repository.IVentaRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PagoServiceImpl implements IPagoService {

    @Autowired
    private IPagoRepository repository;

     private final Logger log = LogManager.getLogger(PagoServiceImpl.class);

    @Override
    public PagoResponse save(PagoRequest request) {

        log.info("pago request: ", request);
        //1. validar el fromatod del json validapo
        this.validateRequest(request);

        //2. transformar a entidades
        final List<Pago> pagos = PagoMapper.toEntities.apply(request);
        return PagoMapper
                .toListResponse
                .apply(repository.saveAll(pagos));
    }

    @Autowired
    private IVentaRepository ventarepository;

    private void validateRequest(PagoRequest request) {

        if (request.getVentaId() == null) {
            throw new RuntimeException("Venta Id es null.");
        }

        ventarepository.findById(request.getVentaId())
                .orElseThrow(()-> new RuntimeException("Venta no existe."));

        if (request.getTotalPago() == null) {
            throw new RuntimeException("Total Pago es null.");
        }

        final BigDecimal montoDetalleTotal = request.getDetallePago().stream()
                .map( detalle -> {
                    if(detalle.getTipo() == null) {
                        throw new RuntimeException("Detalle tipo Pago es null.");
                    }
                    return Optional.ofNullable(detalle.getMonto())
                            .orElseThrow( ()-> new RuntimeException("Detalle monto es null."));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (request.getTotalPago().equals(BigDecimal.ZERO)  || montoDetalleTotal.equals(BigDecimal.ZERO)) {
            throw new RuntimeException("Montos no deben ser 0.");
        }

        if (!request.getTotalPago().equals(montoDetalleTotal)) {
            throw new RuntimeException("Monto Total pago debe ser igual a Monto Detalle.");
        }
    }

}
