package bo.com.micrium.modulobase.modulos.producto.validators;

import bo.com.micrium.modulobase.common.exceptions.LdapContextException;
import bo.com.micrium.modulobase.commons.GlobalValidator;
import bo.com.micrium.modulobase.commons.GrupoEstado;
import bo.com.micrium.modulobase.controllers.dto.GrupoRequest;
import bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto.ProductoRequest;
import bo.com.micrium.modulobase.security.services.ActiveDirectoryService;
import bo.com.micrium.modulobase.services.ParametroService;
import com.micrium.bd.access.enuns.Parametro;
import com.micrium.bd.access.jpa.modulo.administracion.models.Grupo;
import com.micrium.bd.access.jpa.modulo.productos.models.Producto;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Optional;
@Component
public class ProductoValidator extends GlobalValidator {

    @Autowired
    private IProductoRepository repository;

    @Autowired
    ParametroService parametroService;

    public void validate(ProductoRequest input, Long id, Errors errors) {
        if (isBlanck(input.getNombre()) || input.getNombre().length() > 60) {
            errors.rejectValue("nombre", "field.nombre", "La longitud del nombre debe ser mayor a 0 y menor a 60.");
            return;
        }

        if (!input.getNombre().matches(parametroService
                .getParametroByNombre(Parametro.DelSistema.EXPRESION_REGULAR_GENERAL.name()).getValor())) {
            errors.rejectValue("nombre", "field.nombre", parametroService
                    .getParametroByNombre(Parametro.DelSistema.MENSAJE_VALIDACION_GENERAL.name()).getValor());
        }
    }

}
