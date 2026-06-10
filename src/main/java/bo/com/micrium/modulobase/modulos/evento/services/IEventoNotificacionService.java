package bo.com.micrium.modulobase.modulos.evento.services;

import bo.com.micrium.modulobase.modulos.evento.controllers.dtos.EventoNotificacionRequest;
import bo.com.micrium.modulobase.modulos.evento.controllers.dtos.EventoNotificacionResponse;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.list.ListVentaRequest;
import bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.venta.list.ListVentaResponse;
import com.micrium.bd.access.jpa.modulo.eventos.models.EventoNotificacion;
import com.micrium.bd.access.jpa.modulo.eventos.models.Notificacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEventoNotificacionService {

    Page<EventoNotificacionResponse> list(EventoNotificacionRequest params, Pageable page);

    EventoNotificacion registrarEvento(String typeEvento, Long productoId);

    void procesarEventosPendientes();

    EventoNotificacionResponse notificacionLeido(Long notificacionId);

}
