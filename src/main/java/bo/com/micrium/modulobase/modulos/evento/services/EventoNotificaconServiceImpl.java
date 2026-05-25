package bo.com.micrium.modulobase.modulos.evento.services;

import bo.com.micrium.modulobase.common.enums.EnumEvento;
import bo.com.micrium.modulobase.common.providers.CurrentUserProvider;
import bo.com.micrium.modulobase.modulos.evento.controllers.dtos.EventoNotificacionRequest;
import bo.com.micrium.modulobase.modulos.evento.controllers.dtos.EventoNotificacionResponse;
import com.micrium.bd.access.jpa.modulo.eventos.models.EventoNotificacion;
import com.micrium.bd.access.jpa.modulo.eventos.models.Notificacion;
import com.micrium.bd.access.jpa.modulo.eventos.repositories.IEventoNotificacionRepository;
import com.micrium.bd.access.jpa.modulo.eventos.repositories.INotificacionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class EventoNotificaconServiceImpl implements IEventoNotificacionService {

    private final IEventoNotificacionRepository repository;

    private final INotificacionRepository notificacionRepository;

    private final CurrentUserProvider currentUserProvider;

    private final Logger log = LogManager.getLogger(EventoNotificaconServiceImpl.class);

    @Override
    public Page<EventoNotificacionResponse> list(EventoNotificacionRequest request, Pageable page) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        log.info("list request "+ request);

        return repository.filter(
                        queryfilterTexto(request.getId()),
                        filterTextoQueryUpperLike(request.getId()),
                        queryfilterTexto(request.getPresentacionId()),
                        filterTextoQueryUpperLike(request.getPresentacionId()),
                        queryfilterTexto(request.getTipoEvento()),
                        filterTextoQueryUpperLike(request.getTipoEvento()),
                        queryfilterTexto(request.getNotificacionId()),
                        filterTextoQueryUpperLike(request.getNotificacionId()),
                        queryfilterTexto(request.getTipoNotificacion()),
                        filterTextoQueryUpperLike(request.getTipoNotificacion()),
                        page, tenantId)
                .map(ele -> {
                    EventoNotificacionResponse resp = new EventoNotificacionResponse();
                    resp.setId(ele.getId());
                    resp.setNotificacionId(ele.getNotificacionId());
                    resp.setPresentacionId(ele.getPresentacionId());
                    resp.setTitulo(ele.getTitulo());
                    resp.setMensaje(ele.getMensaje());
                    resp.setDescripcion(ele.getDescripcion());
                    resp.setTipoEvento(ele.getTipoEvento());
                    resp.setTipoNotificacion(ele.getTipoNotificacion());
                    return resp;
                });
    }

    @Override
    public EventoNotificacion registrarEvento(String typeEvento, Long presentacionId) {

        final Optional<EventoNotificacion> eventExist = repository.
                findByTipoEventoAndPresentacionId(typeEvento, presentacionId);

        if (eventExist.isPresent()) {
            log.info("registrarEvento ya existe: " + eventExist.get());
            return eventExist.get();
        }
        final Long tenantId = currentUserProvider.getUserTenantId();
        EventoNotificacion event = new EventoNotificacion();
        event.setTipoEvento(typeEvento);
        event.setEstado(EnumEvento.NotificacionStatus.PENDIENTE.name());
        event.setPresentacionId(presentacionId);
        event.setTenantId(tenantId);

        return repository.save(event);
    }

    public void registrarEventoYNotificacionProducto(String typeEvento, Long presentacionId) {
        EventoNotificacion newEvento = this.registrarEvento(typeEvento, presentacionId);
        this.crearNotificacionProducto(newEvento);
        newEvento.setEstado(EnumEvento.NotificacionStatus.PROCESADO.name());
        repository.save(newEvento);
    }

    @Override
    public void procesarEventosPendientes() {

        List<EventoNotificacion> eventosNotificacion = repository.findByEstado(EnumEvento.NotificacionStatus.PENDIENTE.name());

        log.info("procesarEventosPendientes size: " + eventosNotificacion.size());

        for (EventoNotificacion event: eventosNotificacion) {
            this.crearNotificacionVentaProducto(event);

            event.setEstado(EnumEvento.NotificacionStatus.PROCESADO.name());

            this.repository.save(event);
        }
        // this.repository.saveAll(eventosNotificacion);
    }

    private void crearNotificacionVentaProducto(EventoNotificacion eventoNotificacion) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        Notificacion notificacion = new Notificacion();
        notificacion.setEventoNotificacionId(eventoNotificacion.getId());
        notificacion.setFechaEnvio(new Timestamp(System.currentTimeMillis()));
        notificacion.setTitulo("Notificacion Producto PR-" + eventoNotificacion.getPresentacionId());

        notificacion.setMensaje("El producto esta " + eventoNotificacion.getTipoEvento());
        notificacion.setDescripcion("es una descripcion cualquiera");

        final String tipoNotificacion = eventoNotificacion.
                getTipoEvento().equals(EnumEvento.Type.SIN_STOCK.name()) ?
                EnumEvento.NotificacionTipo.ALERTA.name() :
                EnumEvento.NotificacionTipo.RECORDATORIO.name();

        notificacion.setTipo(tipoNotificacion);
        notificacion.setTenantId(tenantId);
        //notificacion.setUsuarioId(null);
        log.info("CreandoNotificacion: " + notificacion);
        this.notificacionRepository.save(notificacion);
    }

    private void crearNotificacionProducto(EventoNotificacion eventoNotificacion) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        Notificacion notificacion = new Notificacion();
        notificacion.setEventoNotificacionId(eventoNotificacion.getId());
        notificacion.setFechaEnvio(new Timestamp(System.currentTimeMillis()));
        notificacion.setTitulo("Notificacion Producto PR-" + eventoNotificacion.getPresentacionId());

        notificacion.setMensaje("El producto esta " + eventoNotificacion.getTipoEvento());
        notificacion.setDescripcion("Este producto no esta configurado adecuadamente");

        notificacion.setTipo(EnumEvento.NotificacionTipo.RECOMENDACION.name());
        notificacion.setTenantId(tenantId);
        //notificacion.setUsuarioId(null);
        log.info("CreandoNotificacion: " + notificacion);
        this.notificacionRepository.save(notificacion);
    }

    public EventoNotificaconServiceImpl(
            IEventoNotificacionRepository repository,
            INotificacionRepository notificacionRepository,
            CurrentUserProvider currentUserProvider) {
        this.repository = repository;
        this.notificacionRepository = notificacionRepository;
        this.currentUserProvider = currentUserProvider;
    }

    private boolean isBlanck(String dato) {
        return dato == null || dato.trim().isEmpty();
    }

    private int queryfilterTexto(String texto) {
        return this.isBlanck(texto) ? -1 : 0;
    }

    private String filterTextoQueryUpperLike(String texto) {
        return this.isBlanck(texto) ? "" : "%" + texto.trim().toUpperCase() + "%";
    }
}
