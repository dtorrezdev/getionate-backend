package com.dtorrez.main.modulos.ventas.services.venta.anular;

import com.dtorrez.main.common.exceptions.EntityNotFoundException;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.AnularVentaRequest;
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
                .orElseThrow(() -> new EntityNotFoundException("Venta","id", request.getVentaId()));

        clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente","id", request.getClienteId()));

        venta.setGlosa(request.getGlosa());
        venta.setEstado("ANULADO");

        // Modululo de inventari
        // generar un movimiento de entrada
        // aumentar stock del producto (Por devoulcion)

        ventaRepository.save(venta);
    }
}
