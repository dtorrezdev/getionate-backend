package bo.com.micrium.modulobase.modulos.ventas.services.venta;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.VentaRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.crear.VentaResponse;
import bo.com.micrium.modulobase.modulos.ventas.mapper.VentaMapper;
import com.micrium.bd.access.jpa.modulo.productos.models.ProductoPresentacion;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import com.micrium.bd.access.jpa.modulo.venta.models.DetalleVenta;
import com.micrium.bd.access.jpa.modulo.venta.models.Venta;
import com.micrium.bd.access.jpa.modulo.venta.repository.IClienteRepository;
import com.micrium.bd.access.jpa.modulo.venta.repository.IDetalleVenta;
import com.micrium.bd.access.jpa.modulo.venta.repository.IVentaRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrearVentaServiceImpl implements ICrearVentaService {

    @Autowired
    private IVentaRepository repository;

    @Autowired
    private IDetalleVenta detalleRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IProductoPresentacionRepository productoRepository; // eliminar dependencia

     private final Logger log = LogManager.getLogger(CrearVentaServiceImpl.class);


    @Override
    public VentaResponse execute(VentaRequest crearVentaRequest) {

        final Venta newVenta = VentaMapper.toEntity
                    .apply(crearVentaRequest);

        clienteRepository.findById(newVenta.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no existe"));

        log.info("venta " + newVenta.toString());

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
                    detalle.setSubtotal( detalle.getPrecioUnitario() * detalle.getCantidadBase());
                    return detalle;
                }).toList();

//        BigDecimal total = detalles.stream()
//                .map(DetalleVenta::getSubtotal)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);

        log.info("detalles " + detalles);
//        newVenta.setTotal(total);
        return VentaMapper.toResponse
                .apply(repository.save(newVenta));
    }
}
