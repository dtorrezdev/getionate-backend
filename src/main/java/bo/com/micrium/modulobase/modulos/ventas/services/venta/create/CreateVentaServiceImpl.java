package bo.com.micrium.modulobase.modulos.ventas.services.venta.create;

import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.venta.MovimientoVentaResponse;
import bo.com.micrium.modulobase.modulos.inventario.services.movimiento.ICreateMovimientoService;
import bo.com.micrium.modulobase.modulos.inventario.services.movimiento.ICreateMovimientoVentaService;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.*;
import bo.com.micrium.modulobase.modulos.ventas.mapper.VentaMapper;
import com.micrium.bd.access.jpa.modulo.productos.models.ProductoPresentacion;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import com.micrium.bd.access.jpa.modulo.venta.repository.*;
import com.micrium.bd.access.jpa.modulo.venta.models.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
public class CreateVentaServiceImpl implements ICreateVentaService {

    // Dominio del Modulo Venta
    @Autowired
    private IVentaRepository repository;

    @Autowired
    private IDetalleVenta detalleRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    // Dominio del Modulo Producto
    @Autowired
    private IProductoPresentacionRepository productoRepository; // eliminar dependencia

     private final Logger log = LogManager.getLogger(CreateVentaServiceImpl.class);

    // Dominio del Modulo Inventario
    @Autowired
    private ICreateMovimientoVentaService createMovimientoService;


    @Override
    @Transactional
    public VentaResponse execute(VentaRequest crearVentaRequest) {

        final Venta newVenta = VentaMapper.toEntity
                .apply(crearVentaRequest);

        if(crearVentaRequest.getEstado().equals("VENTA") ) {
            MovimientoVentaResponse resp = this.createMovimientoService
                    .execute(crearVentaRequest.getDetalle());

            if(Objects.isNull(resp) || resp.getId() == null) {
                throw new RuntimeException("Movimiento no se ha creado");
            }
            log.info("movimiento response: "+ resp);
            newVenta.setMovimientoId(resp.getId());
        }

        log.info("venta " + newVenta.toString());
        clienteRepository.findById(newVenta.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no existe"));

        List<DetalleVenta> detalles = newVenta.getDetalle().stream()
                .map( (detalle) -> {

                    // Validamos producto (llevar logica a Modulo Producto)
                    ProductoPresentacion productoPresentacion = productoRepository.findById(detalle.getPresentacionId())
                            .orElseThrow(()-> new RuntimeException("Producto no existe"));

//                    if(productoPresentacion.getStock() < detalle.getCantidadBase()) {
//                        throw new RuntimeException("Producto no existe");
//                    }

                    /*
                    if (Parametro.isCalculateWithPrecioProducto()) {
                        BigDecimal precio = productoPresentacion.getPrecioVenta();
                        BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(detalle.getCantidad()));
                        detalle.setPrecio(precio);
                        detalle.setSubtotal(subtotal);
                    }*/

                    // calculo de precio ya lo hago en el Frontend
                    BigDecimal precio = productoPresentacion.getPrecioVenta();
                  BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(detalle.getCantidad()));
                    detalle.setSubtotal(subtotal);
                    return detalle;
                }).toList();

//        BigDecimal total = detalles.stream()
//                .map(DetalleVenta::getSubtotal)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);

        log.info("detalles " + detalles);
//        VentaResponse response = new VentaResponse();
//        response.setId(0L);
//        return response;
//        newVenta.setTotal(total);
        return VentaMapper.toResponse
                .apply(repository.save(newVenta));
    }
}
