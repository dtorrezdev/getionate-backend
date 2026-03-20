package bo.com.micrium.modulobase.modulos.ventas.services.venta;

import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.ListVentaRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.ListVentaResponse;
import com.micrium.bd.access.jpa.modulo.venta.repository.IVentaRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ListVentaServiceImpl implements IListVentaService {

    @Autowired
    private IVentaRepository ventaRepository;

     private final Logger log = LogManager.getLogger(ListVentaServiceImpl.class);

    @Override
    public Page<ListVentaResponse> execute(ListVentaRequest params, Pageable page) {
        log.info("params: " + params);
        log.info("page: " + page);

        return null;
    }
}
