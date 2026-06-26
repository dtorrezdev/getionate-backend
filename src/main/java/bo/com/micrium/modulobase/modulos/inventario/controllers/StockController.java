package bo.com.micrium.modulobase.modulos.inventario.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.IGetMethod;
import bo.com.micrium.modulobase.controllers.template.IUpdateMethod;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleDto;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleResponse;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockUpdateRequest;
import bo.com.micrium.modulobase.modulos.inventario.services.stock.IStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/stocks")
public class StockController implements
        IGetMethod<StockDisponibleResponse, Long>,
        IUpdateMethod<StockUpdateRequest, StockDisponibleResponse, Long>
{
    private final IStockService service;

    @GetMapping
    public ResponseEntity<ApiResponse<Map<Long, List<StockDisponibleDto>>>>  list() {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.stockDisponibles(),
                        "Stocks listado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<StockDisponibleResponse>> get(
            String token,
            String ipClient,
            String form,
            Long id) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        new StockDisponibleResponse(
                                this.service.stockDisponibleByPresentacionId(id)),
                        "Stocks listado correctamente.")
                );
    }

    @Override
    public ResponseEntity<ApiResponse<StockDisponibleResponse>> update(
            String token,
            String ipClient,
            String form,
            StockUpdateRequest request, Long id
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                                this.service.update(request, id),
                        "Stocks Producto actualizado correctamente.")
                );
    }

    public StockController(IStockService service) {
        this.service = service;
    }

}
