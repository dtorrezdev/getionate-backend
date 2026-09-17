package com.dtorrez.main.modulos.administracion.controllers.dtos.tenant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class ThemeRequest {
    private String primaryColor;
    private String surfaceStyle;
    private Boolean darkMode;
    private String logo;
    private String font;
}
