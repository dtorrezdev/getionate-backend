package com.dtorrez.main.modulos.evento.controllers;

import com.dtorrez.main.common.response.ApiResponse;
import com.dtorrez.main.controllers.template.IListMethod;
import com.dtorrez.main.controllers.template.IUpdateMethod;
import com.dtorrez.main.modulos.evento.controllers.dtos.EventoNotificacionRequest;
import com.dtorrez.main.modulos.evento.controllers.dtos.EventoNotificacionResponse;
import com.dtorrez.main.modulos.evento.services.IEventoNotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/evento_notificacion")
public class EventoNotificacionController implements
        IListMethod<EventoNotificacionRequest, EventoNotificacionResponse>,
        IUpdateMethod<EventoNotificacionRequest, EventoNotificacionResponse , Long>
{

    @Autowired
    private IEventoNotificacionService service;

    @Override
    public ResponseEntity<ApiResponse<Page<EventoNotificacionResponse>>> list(
            String token,
            String tenantId,
            String ipClient,
            String form,
            EventoNotificacionRequest request,
            Pageable pageRequest
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.list(request, pageRequest),
                        "Notificaciones listado correctamente.")
                );
    }

    public ResponseEntity<ApiResponse<EventoNotificacionResponse>> update(
            String token,
            String ipClient,
            String form,
            EventoNotificacionRequest request,
            Long id
    ) {
        return ResponseEntity.status(200)
                .body(ApiResponse.ok(
                        this.service.notificacionLeido(id),
                        "Notificacion actualizado.")
                );
    }
}
