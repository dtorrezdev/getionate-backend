/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.com.micrium.modulobase.validators;

import bo.com.micrium.modulobase.commons.GlobalValidator;
import com.micrium.bd.access.jpa.repositories.IEtiquetaRepository;
import com.micrium.bd.access.jpa.models.dto.EtiquetaRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 *
 * @author alepaco.maton
 */
@Component
public class EtiquetaValidator extends GlobalValidator {

    @Autowired
    IEtiquetaRepository repository;

    public void validate(EtiquetaRequest input, Long id, Errors errors) {

    }

}
