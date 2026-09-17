package com.dtorrez.main.modulos.producto.controllers.dtos.producto_presentacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class DeleteProductoRequest implements Serializable {
    @NotNull(message = "El id no puede ser nulo")
    private Long id;
}
