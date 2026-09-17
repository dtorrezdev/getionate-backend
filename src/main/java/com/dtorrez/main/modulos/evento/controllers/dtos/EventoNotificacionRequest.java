package com.dtorrez.main.modulos.evento.controllers.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
@ToString
public class EventoNotificacionRequest implements Serializable {
    private String id;
    private String presentacionId;
    private String tipoEvento;
    private String notificacionId;
    private String tipoNotificacion;
}
