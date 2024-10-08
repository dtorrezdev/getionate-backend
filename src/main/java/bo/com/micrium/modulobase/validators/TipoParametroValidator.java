/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.com.micrium.modulobase.validators;

import bo.com.micrium.modulobase.commons.GlobalValidator;
import bo.com.micrium.modulobase.models.TipoParametro;
import bo.com.micrium.modulobase.repositories.ITipoParametroRepository;
import bo.com.micrium.modulobase.models.dto.TipoParametroRequest;
import bo.com.micrium.modulobase.services.ParametroService;
import bo.com.micrium.modulobase.commons.ParametroID;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 *
 * @author alepaco.maton
 */
@Component
public class TipoParametroValidator extends GlobalValidator {

    @Autowired
    ITipoParametroRepository repository;

    @Autowired
    ParametroService parametroService;

    public void validate(TipoParametroRequest input, Long id, Errors errors) {
        if (id != null) {
            Optional<TipoParametro> model = repository.findById(id);

            if (!model.isPresent()) {
                errors.rejectValue("id", "field.invalido", "Identificador de usuario invalido.");
            } else {
                TipoParametro temp = repository.findByNombre(input.getNombre());

                if (temp != null && !temp.getId().equals(model.get().getId())) {
                    errors.rejectValue("nombre", "field.invalido", "Ya se encuentra en uso.");
                }
            }
        } else {
            TipoParametro temp = repository.findByNombre(input.getNombre());

            if (temp != null) {
                errors.rejectValue("nombre", "field.invalido", "Ya se encuentra en uso.");
            }
        }

        if (isBlanck(input.getNombre())) {
            errors.rejectValue("nombre", "field.nombre", "La longitud del nombre debe ser mayor a 0 y menor a 50.");
        }

        if (errors.hasErrors()) {
            return;
        }

        if (!input.getNombre().matches(parametroService.getParametro(ParametroID.EXPRESION_REGULAR_GENERAL).getValor())) {
            errors.rejectValue("nombre", "field.nombre", parametroService.getParametro(ParametroID.MENSAJE_VALIDACION_GENERAL).getValor());
        }
    }

}
