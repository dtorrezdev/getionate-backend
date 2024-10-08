/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.com.micrium.modulobase.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author alepaco.maton
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "MU_ETIQUETA")
public class Etiqueta implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "MU_ETIQUETA_AD_ETIQUETAID_GENERATOR", sequenceName = "SEQ_MU_ETIQUETA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MU_ETIQUETA_AD_ETIQUETAID_GENERATOR")
    private Long id;
    private String llave;
    private String valor;
    private String grupo;
    @JsonIgnore
    private Boolean estado;
    
}
