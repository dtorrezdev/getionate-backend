package bo.com.micrium.modulobase.modulos.ventas.services;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.VentaRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.VentaResponse;
import bo.com.micrium.modulobase.modulos.ventas.mapper.VentaMapper;
import com.micrium.bd.access.jpa.modulo.productos.models.Producto;
import com.micrium.bd.access.jpa.modulo.productos.models.ProductoPresentacion;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoRepository;
import com.micrium.bd.access.jpa.modulo.venta.models.DetalleVenta;
import com.micrium.bd.access.jpa.modulo.venta.models.Venta;
import com.micrium.bd.access.jpa.modulo.venta.repository.IDetalleVenta;
import com.micrium.bd.access.jpa.modulo.venta.repository.IVentaRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrearVentaServiceImpl implements ICrearVentaService {

    @Autowired
    private IVentaRepository repository;

    @Autowired
    private IDetalleVenta detalleRepository;

    @Autowired
    private IProductoPresentacionRepository productoRepository;

     private final Logger log = LogManager.getLogger(CrearVentaServiceImpl.class);


    @Override
    public VentaResponse execute(VentaRequest crearVentaRequest) {

        final Venta newVenta = VentaMapper.toEntity
                    .apply(crearVentaRequest);

        log.info("venta " + newVenta.toString());

        List<DetalleVenta> detalles = newVenta.getDetalle().stream()
                .map( (detalle) -> {

                    // Validamos producto (llevar logica a Modulo Producto)
                    ProductoPresentacion productoPresentacion = productoRepository.findById(detalle.getPresentacionId())
                            .orElseThrow(()-> new RuntimeException("Producto no existe"));

//                    if(productoPresentacion.getStock() < detalle.getCantidadBase()) {
//                        throw new RuntimeException("Producto no existe");
//                    }

                    // calculo de precio ya lo hago en el Frontend

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
