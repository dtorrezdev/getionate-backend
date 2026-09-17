package com.dtorrez.main.modulos.administracion.services.reporte;

import com.dtorrez.main.modulos.administracion.controllers.dtos.reporte.EstadisticaResponse;
import com.dtorrez.main.modulos.administracion.controllers.dtos.reporte.ProductoMasVendidoResponse;
import com.dtorrez.main.modulos.inventario.controllers.dtos.movimiento.list.MovimientoProductoResponse;

import java.util.List;

public interface IReporteService {

    EstadisticaResponse getEstadisticas();

    List<ProductoMasVendidoResponse> getProductoMasVendidos();

    List<MovimientoProductoResponse> getKardexProducto(Long productoId);

}
