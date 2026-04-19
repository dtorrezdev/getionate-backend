package bo.com.micrium.modulobase.modulos.inventario.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.stock.StockDisponibleResponse;
import bo.com.micrium.modulobase.modulos.inventario.services.stock.GetStockDisponibleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/stocks")
public class StockController
        {

    @Autowired
    private GetStockDisponibleService listService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<StockDisponibleResponse>>> list(
            @ModelAttribute StockDisponibleRequest request
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.listService.execute(request),
                        "Se ha listado correctamente")
                );
    }
}
