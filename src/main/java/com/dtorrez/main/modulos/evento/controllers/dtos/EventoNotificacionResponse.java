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
public class EventoNotificacionResponse implements Serializable {
    private Long id;
    private Long presentacionId;
    private String tipoEvento;
    private Long notificacionId;
    private String titulo;
    private String mensaje;
    private String descripcion;
    private String tipoNotificacion;
}
