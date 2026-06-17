package bo.com.micrium.modulobase.modulos.administracion.services.reporte;

import bo.com.micrium.modulobase.modulos.administracion.controllers.dtos.reporte.EstadisticaResponse;
import bo.com.micrium.modulobase.modulos.administracion.controllers.dtos.reporte.ProductoMasVendidoResponse;

import java.util.List;

public interface IReporteService {

    EstadisticaResponse getEstadisticas();

    List<ProductoMasVendidoResponse> getProductoMasVendidos();

}
