package bo.com.micrium.modulobase.modulos.administracion.services.reporte;

import bo.com.micrium.modulobase.common.providers.CurrentUserProvider;
import bo.com.micrium.modulobase.modulos.administracion.controllers.dtos.reporte.EstadisticaResponse;
import bo.com.micrium.modulobase.modulos.administracion.controllers.dtos.reporte.ProductoMasVendidoResponse;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.list.MovimientoProductoResponse;
import com.micrium.bd.access.jpa.modulo.compra.repositories.ICompraRepository;
import com.micrium.bd.access.jpa.modulo.inventario.repository.IMovimientoRepository;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
import com.micrium.bd.access.jpa.modulo.venta.repository.IClienteRepository;
import com.micrium.bd.access.jpa.modulo.venta.repository.IVentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteServiceImpl implements IReporteService {

    @Autowired
    private IVentaRepository ventaRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private ICompraRepository compraRepository;

    @Autowired
    private IMovimientoRepository movimientoRepository;

    @Autowired
    private IProductoPresentacionRepository productoPresentacionRepository;

    @Autowired
    private CurrentUserProvider currentUserProvider;

    @Override
    public EstadisticaResponse getEstadisticas() {
        final Long tenantId = currentUserProvider.getUserTenantId();
        EstadisticaResponse response = new EstadisticaResponse();
        response.setTotalVentas(ventaRepository.sumTotalByTenantId(tenantId));
        response.setCantidadCliente(clienteRepository.countByTenantId(tenantId));
        response.setCantidadCompras(compraRepository.countByTenantId(tenantId));
        response.setCantidadProducto(productoPresentacionRepository.count());

        return response;
    }

    @Override
    public List<ProductoMasVendidoResponse> getProductoMasVendidos() {
        final Long tenantId = currentUserProvider.getUserTenantId();
        final Long sumCantidadVendido = this.ventaRepository.sumCantidadVendidaByTenantId(tenantId);
        return this.ventaRepository.getProductoMasVendido(tenantId)
                .stream().map(prod -> {
                    ProductoMasVendidoResponse resp = new ProductoMasVendidoResponse();
                    resp.setProducto(prod.getProducto());
                    resp.setCantidadVendida(prod.getCantidadVendida());
                    resp.setPorcentaje(
                            sumCantidadVendido > 0 ?
                                    (prod.getCantidadVendida() * 100.0) / sumCantidadVendido : 0);
                    return resp;
                }).toList();
    }

    @Override
    public List<MovimientoProductoResponse> getKardexProducto(Long productoId) {
        final Long tenantId = currentUserProvider.getUserTenantId();

        return this.movimientoRepository.getKardex(productoId, tenantId)
            .stream().map(mov -> {;
                    MovimientoProductoResponse resp = new MovimientoProductoResponse();
                    resp.setFecha(mov.getFecha());
                    resp.setTipoMovimiento(mov.getTipoMovimiento());
                    resp.setMotivo(mov.getMotivo());
                    resp.setCantidad(mov.getCantidad());
                    resp.setPrecioVenta(mov.getPrecioVenta());
                    resp.setDesde(mov.getDesde());
                    resp.setHasta(mov.getHasta());
                    resp.setLote(mov.getLote());
                    resp.setUnidadMedida(mov.getUnidadMedida());
                    resp.setUnidadMedidaShort(mov.getUnidadMedidaShort());
                    return resp;
                }).toList();
    }


}
