package bo.com.micrium.modulobase.modulos.inventario.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.ICreateController;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.MovimientoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.movimiento.MovimientoResponse;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleByProductoRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleByProductoResponse;
import bo.com.micrium.modulobase.modulos.inventario.services.ICreateMovimientoService;
import bo.com.micrium.modulobase.modulos.inventario.services.stock.ListStockDisponibleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/movimientos")
public class MovimientoController implements
        ICreateController<MovimientoRequest, MovimientoResponse> {

    @Autowired
    private ICreateMovimientoService createService;

    @Override
    public ResponseEntity<ApiResponse<MovimientoResponse>> create(
            String token,
            String ipClient,
            String form,
            MovimientoRequest request
    ) {
        return ResponseEntity.status(201)
                .body(ApiResponse.ok(
                        this.createService.execute(request),
                        "Se ha creado correctamente")
                );
    }

    @Autowired
    private ListStockDisponibleService listService;

    @GetMapping("/stocks_by_producto")
    public ResponseEntity<ApiResponse<List<StockDisponibleByProductoResponse>>> list(
            @ModelAttribute StockDisponibleByProductoRequest request
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.listService.execute(request),
                        "Se ha listado correctamente")
                );
    }
}
