package bo.com.micrium.modulobase.modulos.evento.services;

import bo.com.micrium.modulobase.common.enums.EnumEvento;
import bo.com.micrium.modulobase.modulos.ventas.services.venta.create.CreateVentaServiceImpl;
import com.micrium.bd.access.jpa.modulo.eventos.models.EventoNotificacion;
import com.micrium.bd.access.jpa.modulo.eventos.models.Notificacion;
import com.micrium.bd.access.jpa.modulo.eventos.repositories.IEventoNotificacionRepository;
import com.micrium.bd.access.jpa.modulo.eventos.repositories.INotificacionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class EventoNotificaconServiceImpl implements IEventoNotificacionService {

    private final IEventoNotificacionRepository repository;

    private final INotificacionRepository notificacionRepository;

    private final Logger log = LogManager.getLogger(EventoNotificaconServiceImpl.class);

    @Override
    public EventoNotificacion registrarEvento(String typeEvento, Long presentacionId) {

        final Optional<EventoNotificacion> eventExist = repository.
                findByTipoEventoAndProductoId(typeEvento, presentacionId);

        if (eventExist.isPresent()) {
            log.info("registrarEvento ya existe: " + eventExist.get());
            return eventExist.get();
        }

        EventoNotificacion event = new EventoNotificacion();
        event.setTipoEvento(typeEvento);
        event.setEstado(EnumEvento.NotificacionStatus.PENDIENTE.name());
        event.setProductoId(presentacionId);

        return repository.save(event);
    }

    @Override
    public void procesarEventosPendientes() {

        List<EventoNotificacion> eventosNotificacion = repository.findByEstado(EnumEvento.NotificacionStatus.PENDIENTE.name());

        log.info("procesarEventosPendientes size: " + eventosNotificacion.size());

        for (EventoNotificacion event: eventosNotificacion) {
            this.crearNotificacion(event);

            event.setEstado(EnumEvento.NotificacionStatus.PROCESADO.name());

            this.repository.save(event);
        }
        // this.repository.saveAll(eventosNotificacion);
    }

    private void crearNotificacion(EventoNotificacion eventoNotificacion) {
        Notificacion notificacion = new Notificacion();
        notificacion.setEventoNotificacionId(eventoNotificacion.getId());
        notificacion.setFechaEnvio(new Timestamp(System.currentTimeMillis()));
        notificacion.setTitulo("Notificacion Producto PR-" + eventoNotificacion.getProductoId());

        notificacion.setMensaje("El producto esta " + eventoNotificacion.getTipoEvento());
        notificacion.setDescripcion("es una descripcion cualquiera");

        final String tipoNotificacion = eventoNotificacion.
                getTipoEvento().equals(EnumEvento.Type.SIN_STOCK.name()) ?
                EnumEvento.NotificacionTipo.ALERTA.name() :
                EnumEvento.NotificacionTipo.RECORDATORIO.name();

        notificacion.setTipo(tipoNotificacion);
        //notificacion.setUsuarioId(null);
        log.info("CreandoNotificacion: " + notificacion);
        this.notificacionRepository.save(notificacion);
    }

    public EventoNotificaconServiceImpl(IEventoNotificacionRepository repository, INotificacionRepository notificacionRepository) {
        this.repository = repository;
        this.notificacionRepository = notificacionRepository;
    }

}
