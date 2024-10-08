package bo.com.micrium.modulobase.services;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.HashMap;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bo.com.micrium.modulobase.repositories.IParametroRepository;
import bo.com.micrium.modulobase.commons.ParametroTipo;
import bo.com.micrium.modulobase.models.Parametro;
import bo.com.micrium.modulobase.commons.ParametroID;
import bo.com.micrium.cifrado.ConfigEncriptacion;

/**
 *
 * @author alepaco.maton
 */
@Service
@Component
@Order(2)
@Scope("singleton")
public class ParametroService {
    private static final Logger log = LogManager.getLogger(ParametroService.class);

    // @Autowired
    // InicializacionService inicializacionService;
    @Autowired
    private IParametroRepository repository;

    private HashMap<Long, Parametro> listParametro;

    private final SimpleDateFormat sdf = new SimpleDateFormat(ParametroTipo.FORMATO_FECHA_HORA);

    @PostConstruct
    public void init() {
        log.info("ParametroService inicializado");
    }

    public synchronized Parametro getParametro(Long idParametro) {
        return cargarParametros().get(idParametro);
    }

    public synchronized Object getParamVal(Long idParametro) {
        Parametro p = cargarParametros().get(idParametro);
        log.debug("idParametro: " + idParametro + ", parametro encontrado: " + p);
        switch (p.getTipo()) {
            case ParametroTipo.TIPO_CADENA:
                return p.getValor();

            case ParametroTipo.TIPO_COLOR:
                return p.getValor();

            case ParametroTipo.TIPO_FECHA:
                try {
                    return sdf.parse(p.getValor());
                } catch (ParseException e) {
                    log.error("Erro de parse fecha, " + e.getMessage(), e);
                    return null;
                }
            case ParametroTipo.TIPO_NUMERICO:
                return new BigDecimal(p.getValor());
            case ParametroTipo.TIPO_BOOLEANO:
                return Boolean.parseBoolean(p.getValor());
            case ParametroTipo.TIPO_LISTADO_VALORES_NUMERICOS:
                return p.getValor();
            case ParametroTipo.TIPO_LISTADO_VALORES_TEXTO:
                return p.getValor();
            case ParametroTipo.TIPO_PASSWORD:
                return ConfigEncriptacion.decryptSinExcepcion(p.getValor());
        }

        return null;
    }

    /**
     * Este metodo debe ser invocado cuando se haga alguna modificacion a un
     * Parametro para que el cambio se manifieste en el resto del sistema. Si en
     * el transcurso del Desarrollo se crean terceras clases que son de tipo
     * singleton estas clases deberan proverer mecanismos para reinicar sus
     * atributos propios para que desde aqui sean invocados y asi el cambio del
     * Parametro sean aplicables en todo contexto.
     *
     * *
     */
    public synchronized void restartParameter() {
        // log.info("****** Reiniciarparametros..");
        listParametro = null;

        try {
            Parametro reaload = repository.findById(ParametroID.RELOAD_PARAMETER_ID).get();
            reaload.setValor("true");
            repository.save(reaload);
        } catch (Exception e) {
            log.error("Error al cargar ó guardar el parametro RELOAD con ID=" + ParametroID.RELOAD_PARAMETER_ID, e);
        }
    }

    private synchronized HashMap<Long, Parametro> cargarParametros() {
        boolean reload = false;
        // log.info("reload: " + reload + " listParametro; " + listParametro);
        if (listParametro == null) {
            reload = true;
        } else {
            reload = verifRealoadParameters();
        }

        if (reload) {
            // log.info("\n\n ***Recargando los parametros***");
            listParametro = new HashMap<>();
            for (Parametro item : repository.findAll()) {
                listParametro.put(item.getId(), item);
                // log.info(item);
            }

            // log.info("*** Final de cargar parametros ***\n\n ");
            updateParameterReload();

        }
        return listParametro;
    }

    private boolean verifRealoadParameters() {
        // log.info("verifRealoadParameters");
        Optional<Parametro> p = repository.findById(ParametroID.RELOAD_PARAMETER_ID);
        // log.info("Parametro: " + p);
        if (p.isPresent()) {
            return p.get().getValor().equals("true");
        }

        return false;
    }

    private boolean updateParameterReload() {
        Optional<Parametro> p = repository.findById(ParametroID.RELOAD_PARAMETER_ID);

        if (p.isPresent()) {
            p.get().setValor("false");
            repository.save(p.get());

            log.info("Valor del parametro reaload  actualizado a false");

            return true;
        }

        return false;
    }

}
