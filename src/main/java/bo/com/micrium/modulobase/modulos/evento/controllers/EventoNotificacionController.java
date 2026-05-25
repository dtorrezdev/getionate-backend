package bo.com.micrium.modulobase.modulos.evento.controllers;

import bo.com.micrium.modulobase.common.response.ApiResponse;
import bo.com.micrium.modulobase.controllers.template.IListMethod;
import bo.com.micrium.modulobase.modulos.evento.controllers.dtos.EventoNotificacionRequest;
import bo.com.micrium.modulobase.modulos.evento.controllers.dtos.EventoNotificacionResponse;
import bo.com.micrium.modulobase.modulos.evento.services.IEventoNotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/evento_notificacion")
public class EventoNotificacionController implements
        IListMethod<EventoNotificacionRequest, EventoNotificacionResponse> {

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
}
