package bo.com.micrium.modulobase.modulos.evento.services;

import com.micrium.bd.access.jpa.modulo.eventos.models.EventoNotificacion;

public interface IEventoNotificacionService {

    EventoNotificacion registrarEvento(String typeEvento, Long productoId);

    void procesarEventosPendientes();

}
