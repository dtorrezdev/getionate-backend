package com.dtorrez.main.modulos.administracion.controllers;

import com.dtorrez.main.common.response.ApiResponse;
import com.dtorrez.main.modulos.administracion.controllers.dtos.reporte.EstadisticaResponse;
import com.dtorrez.main.modulos.administracion.controllers.dtos.reporte.ProductoMasVendidoResponse;
import com.dtorrez.main.modulos.administracion.services.reporte.IReporteService;
import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.list.MovimientoProductoResponse;
import com.dtorrez.main.modulos.ventas.controllers.dtos.venta.get.GetVentaRecienteResponse;
import com.dtorrez.main.modulos.ventas.services.venta.get.IGetVentaRecienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/reportes")
public class ReporteController {

    private final IGetVentaRecienteService getVentaRecienteService;

    private final IReporteService reporteService;

    @GetMapping("/venta-recientes")
    public ResponseEntity<ApiResponse<List<GetVentaRecienteResponse>>> recientes() {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.getVentaRecienteService.execute(),
                        "Ventas Recientes obtenida correctamente.")
                );
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<ApiResponse<EstadisticaResponse>> estadisticasDashboard() {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        reporteService.getEstadisticas(),
                        "Estadisticas obtenida correctamente.")
                );
    }

    @GetMapping("/productos-mas-vendidos")
    public ResponseEntity<ApiResponse<List<ProductoMasVendidoResponse>>> productosMasVendidos() {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        reporteService.getProductoMasVendidos(),
                        "Productos mas vendidos obtenida correctamente.")
                );
    }

    @GetMapping("/kadex-producto/{id}")
    public ResponseEntity<ApiResponse<List<MovimientoProductoResponse>>> kardex(@PathVariable Long id) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        reporteService.getKardexProducto(id),
                        "Kardex Producto obtenida correctamente.")
                );
    }

    public ReporteController(
            IGetVentaRecienteService getVentaRecienteService,
            IReporteService reporteService
    ) {
        this.getVentaRecienteService = getVentaRecienteService;
        this.reporteService = reporteService;
    }
}
