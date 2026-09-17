package com.dtorrez.main.modulos.evento.services;

import com.dtorrez.main.modulos.evento.controllers.dtos.EventoNotificacionRequest;
import com.dtorrez.main.modulos.evento.controllers.dtos.EventoNotificacionResponse;
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
