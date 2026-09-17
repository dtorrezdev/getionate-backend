package com.dtorrez.main.modulos.producto.validators;

import bo.com.micrium.modulobase.commons.GlobalValidator;
import com.dtorrez.main.modulos.producto.controllers.dtos.marca.MarcaRequest;
import com.dtorrez.main.services.ParametroService;
import com.micrium.bd.access.enuns.Parametro;
import com.micrium.bd.access.jpa.modulo.productos.repository.IMarcaRepository;
import com.micrium.bd.access.jpa.modulo.productos.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class MarcaValidator extends GlobalValidator {

    @Autowired
    private IMarcaRepository repository;

    @Autowired
    ParametroService parametroService;

    public void validate(MarcaRequest input, Long id, Errors errors) {
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

    /**
     *
     * @param nombre: solo valida la longuitud maxima permitida de 60 (si nombre!=null or notEmpty)
     * @param descripcion: solo valida la longuitud maxima permitida de 255 (si nombre!=null or notEmpty)
     * @throws Exception
     */
    public void validate(String nombre, String descripcion) throws Exception {
        if (!isBlanck(nombre) && (nombre.length() > 60)) {
            throw new Exception("La longitud del nombre no debe ser mayor a 60.");
        }
        if (!isBlanck(descripcion) && (descripcion.length() > 255)) {
            throw new Exception("La longitud de descripcion no debe ser mayor a 255.");
        }

    }

}
