package bo.com.micrium.modulobase.modulos.evento.services;

import bo.com.micrium.modulobase.common.enums.EnumEvento;
import bo.com.micrium.modulobase.common.exceptions.EntityNotFoundException;
import bo.com.micrium.modulobase.common.providers.CurrentUserProvider;
import bo.com.micrium.modulobase.modulos.evento.controllers.dtos.EventoNotificacionRequest;
import bo.com.micrium.modulobase.modulos.evento.controllers.dtos.EventoNotificacionResponse;
import com.micrium.bd.access.jpa.modulo.eventos.models.EventoNotificacion;
import com.micrium.bd.access.jpa.modulo.eventos.models.Notificacion;
import com.micrium.bd.access.jpa.modulo.eventos.repositories.IEventoNotificacionRepository;
import com.micrium.bd.access.jpa.modulo.eventos.repositories.INotificacionRepository;
import com.micrium.bd.access.jpa.modulo.productos.models.ProductoPresentacion;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoPresentacionRepository;
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

    private final IProductoPresentacionRepository presentacionRepository;

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

    @Override
    public EventoNotificacionResponse notificacionLeido(Long notificacionId) {
        final Optional<Notificacion> notificacionOpt = notificacionRepository.findById(notificacionId);
        if(notificacionOpt.isPresent()) {
            Notificacion notificacion = notificacionOpt.get();
            notificacion.setFechaLeido(new Timestamp(System.currentTimeMillis()));
            var notificacionUpdate = notificacionRepository.save(notificacion);
            var response = new EventoNotificacionResponse();
            response.setId(notificacionUpdate.getEventoNotificacionId());
            response.setNotificacionId(notificacionUpdate.getId());
            response.setTitulo(notificacionUpdate.getTitulo());
            response.setMensaje(notificacionUpdate.getMensaje());
            response.setDescripcion(notificacionUpdate.getDescripcion());
            response.setTipoNotificacion(notificacionUpdate.getTipo());

            var optEventoNotification = this.repository.findById(notificacion.getEventoNotificacionId());
            if(optEventoNotification.isPresent()) {
                final var eventoNotification =  optEventoNotification.get();
                eventoNotification.setEstado(EnumEvento.NotificacionStatus.NOTIFICADO.name());
                this.repository.save(eventoNotification);
            }

            return response;
        } else {
            log.warn("notificacionLeido no se encontro la notificacion con id: " + notificacionId);
            return null;
        }
    }

    private void crearNotificacionVentaProducto(EventoNotificacion eventoNotificacion) {
        final Long tenantId = currentUserProvider.getUserTenantId();
        Notificacion notificacion = new Notificacion();
        notificacion.setEventoNotificacionId(eventoNotificacion.getId());
        notificacion.setFechaEnvio(new Timestamp(System.currentTimeMillis()));

        final Optional<ProductoPresentacion> optPresentacion = this.presentacionRepository.findById(eventoNotificacion.getPresentacionId());

        if(optPresentacion.isEmpty()) {
            throw new EntityNotFoundException("ProductoPresentacion", "presentacion_id", eventoNotificacion.getPresentacionId());
        }
        final var presentacion = optPresentacion.get();
        notificacion.setTitulo("El Producto " + presentacion.getNombre());
        notificacion.setMensaje("esta "+ EnumEvento.Type.getMessageByTypeEvent(eventoNotificacion.getTipoEvento()));
        notificacion.setDescripcion("notificacion de producto en ventas");

        String tipoNotificacion = EnumEvento.NotificacionTipo.RECOMENDACION.name();

        if(this.isNotificationTypeAlert(eventoNotificacion.getTipoEvento())) {
            tipoNotificacion = EnumEvento.NotificacionTipo.ALERTA.name();
        }

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

        final Optional<ProductoPresentacion> optPresentacion = this.presentacionRepository.findById(eventoNotificacion.getPresentacionId());

        if(optPresentacion.isEmpty()) {
          throw new EntityNotFoundException("ProductoPresentacion", "presentacion_id", eventoNotificacion.getPresentacionId());
        }
        final var presentacion = optPresentacion.get();
        notificacion.setTitulo("El Producto " + presentacion.getNombre());
        notificacion.setMensaje("esta "+ EnumEvento.Type.getMessageByTypeEvent(eventoNotificacion.getTipoEvento()));
        notificacion.setDescripcion("Este producto no esta configurado adecuadamente");

        String tipoNotificacion = EnumEvento.NotificacionTipo.RECOMENDACION.name();

        if(this.isNotificationTypeAlert(eventoNotificacion.getTipoEvento())) {
            tipoNotificacion = EnumEvento.NotificacionTipo.ALERTA.name();
        }

        notificacion.setTipo(tipoNotificacion);
        notificacion.setTenantId(tenantId);
        //notificacion.setUsuarioId(null);
        log.info("CreandoNotificacion: " + notificacion);
        this.notificacionRepository.save(notificacion);
    }

    private boolean isNotificationTypeAlert(String typeEvent) {
        return typeEvent.equals(EnumEvento.Type.PROD_EXPIRADO.name()) ||
                typeEvent.equals(EnumEvento.Type.SIN_STOCK.name());
    }

    public EventoNotificaconServiceImpl(
            IEventoNotificacionRepository repository,
            INotificacionRepository notificacionRepository,
            CurrentUserProvider currentUserProvider,
            IProductoPresentacionRepository presentacionRepository) {
        this.repository = repository;
        this.notificacionRepository = notificacionRepository;
        this.currentUserProvider = currentUserProvider;
        this.presentacionRepository = presentacionRepository;
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
