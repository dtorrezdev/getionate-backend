/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.com.micrium.modulobase.validators;

import bo.com.micrium.modulobase.commons.GlobalValidator;
import bo.com.micrium.modulobase.models.Rol;
import bo.com.micrium.modulobase.repositories.IRolRepository;
import bo.com.micrium.modulobase.models.dto.RolRequest;
import bo.com.micrium.modulobase.services.ParametroService;
import bo.com.micrium.modulobase.commons.ParametroID;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 *
 * @author alepaco.maton
 */
@Component
public class RolValidator extends GlobalValidator {

    @Autowired
    IRolRepository repository;

    @Autowired
    ParametroService parametroService;

    public void validate(RolRequest input, Long id, Errors errors) {
        if (isBlanck(input.getNombre())) {
            errors.rejectValue("nombre", "field.nombre", "El nombre del rol es requerido.");
            return;
        }

        if (input.getNombre().length() > 50) {
            errors.rejectValue("nombre", "field.nombre", "El número de caracteres del nombre debe ser mayor a 0 y menor o igual a 50.");
            return;
        }

        if (!input.getNombre().matches(parametroService.getParametro(ParametroID.EXPRESION_REGULAR_GENERAL).getValor())) {
            errors.rejectValue("nombre", "field.nombre", parametroService.getParametro(ParametroID.MENSAJE_VALIDACION_GENERAL).getValor());
            return;
        }

        if (input.getDescripcion() != null && input.getDescripcion().length() > 200) {
            errors.rejectValue("descripcion", "field.descripcion", "El número de caracteres de la descripción debe ser mayor a 0 y menor o igual a 200.");
            return;
        }

        if (id != null) {
            Optional<Rol> model = repository.findById(id);

            if (!model.isPresent()) {
                errors.rejectValue("id", "field.invalido", "Identificador de usuario invalido.");
            } else {
                Rol temp = repository.findByNombreAndEstadoTrue(input.getNombre());

                if (temp != null && !temp.getId().equals(model.get().getId())) {
                    errors.rejectValue("nombre", "field.invalido", "El nombre del rol, se encuentra en uso.");
                }
            }
        } else {
            Rol temp = repository.findByNombreAndEstadoTrue(input.getNombre());

            if (temp != null) {
                errors.rejectValue("nombre", "field.invalido", "El nombre del rol, se encuentra en uso.");
            }
        }
    }

    public void validatePlus(RolRequest[] input, Errors errors) {
        if (input.length == 0) {
            errors.rejectValue(null, "field.cargaMasivaFail", "No se envio ningun registro.");
            return;
        }

        List<Rol> roles = repository.findAll();

        int line = 2;
        List<String> rolNull = new ArrayList<>();
        List<String> nombreRepetidos = new ArrayList<>();
        for (RolRequest rol : input) {
            if (isBlanck(rol.getNombre())) {
                rolNull.add("" + line);
            }

            Optional<Rol> find = roles.stream().filter(f -> f.getNombre().equals(rol.getNombre())).findFirst();
            if (find.isPresent()) {
                nombreRepetidos.add("El nombre " + rol.getNombre() + " se repite en la fila: " + +line);
            }

            line++;
        }

        if (!rolNull.isEmpty()) {
            errors.rejectValue(null, "field.rollNull", "El nombre es requerido en las filas: " + rolNull);
        }

        if (!nombreRepetidos.isEmpty()) {
            errors.rejectValue(null, "field.nombreRepetidos", "Los nombres mencionados se encuentra en uso: " + nombreRepetidos);
        }
    }

}
