package com.dtorrez.main.modulos.ventas.services.pago;

import com.dtorrez.main.common.enums.EnumVenta;
import com.dtorrez.main.common.exceptions.BusinessRuleException;
import com.dtorrez.main.common.exceptions.EntityNotFoundException;
import com.dtorrez.main.modulos.ventas.controllers.dtos.pago.PagoRequest;
import com.dtorrez.main.modulos.ventas.controllers.dtos.pago.PagoResponse;
import com.dtorrez.main.modulos.ventas.mapper.PagoMapper;
import com.micrium.bd.access.jpa.modulo.venta.models.Pago;
import com.micrium.bd.access.jpa.modulo.venta.models.Venta;
import com.micrium.bd.access.jpa.modulo.venta.repository.IPagoRepository;
import com.micrium.bd.access.jpa.modulo.venta.repository.IVentaRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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
        final Venta venta = ventarepository.findById(request.getVentaId())
                .orElseThrow(()-> new EntityNotFoundException("Venta","id", request.getVentaId()));
        log.info("venta: tenatId " + venta.getTenantId());
        //2. transformar a entidades
        final List<Pago> pagos = request.getDetallePago().stream()
                .map(detalle ->
                    Pago.builder()
                            .tipoPago(detalle.getTipo())
                            .total(detalle.getMonto())
                            .venta(venta)
                            .tenantId(venta.getTenantId())
                            .build()
                ).toList();
        return PagoMapper
                .toListResponse
                .apply(repository.saveAll(pagos));
    }

    @Autowired
    private IVentaRepository ventarepository;

    private void validateRequest(PagoRequest request) {

        if (request.getVentaId() == null) {
            throw new BusinessRuleException(
                    "Venta Pagos",
                    EnumVenta.Rules.VENTA_NOT_FOUND_FOR_PAGOS.name(),
                    Map.of("ventaId", -1));
        }

        if (request.getTotalPago() == null) {
            throw new BusinessRuleException(
                    "Venta Pagos",
                    EnumVenta.Rules.MONTO_NOT_ZERO.name(),
                    Map.of("totalPago", 0));
        }

        final BigDecimal montoDetalleTotal = request.getDetallePago().stream()
                .map( detalle -> {
                    if(detalle.getTipo() == null) {
                        throw new RuntimeException("Detalle tipo Pago es null.");
                    }
                    return Optional.ofNullable(detalle.getMonto())
                            .orElseThrow( ()-> new BusinessRuleException(
                                    "Venta Pagos",
                                    EnumVenta.Rules.MONTO_NOT_ZERO.name(),
                                    Map.of("detalleTotal", 0)));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (request.getTotalPago().equals(BigDecimal.ZERO)  || montoDetalleTotal.equals(BigDecimal.ZERO)) {
            throw new BusinessRuleException(
                    "Venta Pagos",
                    EnumVenta.Rules.MONTO_NOT_ZERO.name(),
                    Map.of("totalPago",request.getTotalPago(),"detalleTotal",montoDetalleTotal));
        }

        if (!request.getTotalPago().equals(montoDetalleTotal)) {
            throw new BusinessRuleException(
                    "Venta Pagos",
                    EnumVenta.Rules.MONTO_TOTAL_EQUALS_DETALLE_TOTAL.name(),
                    Map.of("totalPago",request.getTotalPago(),"DetalleTotal",montoDetalleTotal));
        }
    }

}
