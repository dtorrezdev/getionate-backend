package bo.com.micrium.modulobase.modulos.inventario.controllers;


import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.IListController;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.ubicacion_stock.UbicacionStockRequest;
import bo.com.micrium.modulobase.modulos.inventario.controllers.dtos.ubicacion_stock.UbicacionStockResponse;
import bo.com.micrium.modulobase.modulos.inventario.services.ubicacion_stock.IUbicacionStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ubicacion_stock")
public class UbicacionStockController implements
        IListController<UbicacionStockRequest, UbicacionStockResponse> {

    @Autowired
    private IUbicacionStockService listService;

    @Override
    public ResponseEntity<ApiResponse<Page<UbicacionStockResponse>>> list(
            String token,
            String ipClient,
            String form,
            UbicacionStockRequest request,
            Pageable pageRequest
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.listService.list(request, pageRequest),
                        "Se ha listado correctamente")
                );
    }
}
