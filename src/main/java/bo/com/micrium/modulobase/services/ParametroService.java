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

import com.micrium.bd.access.jpa.repositories.IParametroRepository;
import bo.com.micrium.modulobase.commons.ParametroTipo;
import com.micrium.bd.access.jpa.models.Parametro;
import bo.com.micrium.modulobase.commons.ParametroID;
import bo.com.micrium.cifrado.ConfigEncriptacion;

import com.micrium.bd.access.natives.dao.ParametroDao;
//import com.micrium.bd.access.enuns.Parametro;
import com.micrium.bd.access.enuns.TipoParametro;
import com.micrium.bd.access.exceptions.DaoException;
import com.micrium.bd.access.exceptions.MapperException;
import com.micrium.bd.access.exceptions.ParameterException;
import com.micrium.bd.access.natives.model.MuParametro;
import com.micrium.bd.access.natives.model.dto.ParametroDto;
//import com.micrium.bd.access.natives.service.ParametroService;

import java.util.Optional;

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
    //@Autowired
    //private IParametroRepository repository;

    //private HashMap<Long, Parametro> listParametro;

    private final SimpleDateFormat sdf = new SimpleDateFormat(ParametroTipo.FORMATO_FECHA_HORA);
    
    private com.micrium.bd.access.natives.service.ParametroService paramService;

    public com.micrium.bd.access.natives.service.ParametroService getParametroService() {
        return paramService;
    };

    @PostConstruct
    public void init() {
        log.info("ParametroService inicializado & validar");
        try {
            paramService = new com.micrium.bd.access.natives.service.ParametroService();
            paramService.getParametroByName("BLOQUEO_USUARIOS_DIAS");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            System.exit(1);
        }
    }

    public synchronized ParametroDto getParametroByNombre(String nombreParametro) {
        try {
            return paramService.getParametroByName(nombreParametro);
        } catch (ParameterException | DaoException | MapperException e) {            
            log.error(e);            
        }
        return null;
    }

    public synchronized Object getParamVal(String nombre) {
        ParametroDto p = this.getParametroByNombre(nombre);
        log.debug("idParametro: " + nombre + ", parametro encontrado: " + p);
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

    public void updateParameter(String nombre, String valor) throws DaoException {
        log.info("updateParameter");
        paramService.updateParametroByNombre(nombre, valor);
    }

}
