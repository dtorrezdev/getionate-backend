package com.dtorrez.main.modulos.compra.controllers.dtos.proveedor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class ProveedorRequest implements Serializable {

    private String id;

    @NotNull(message = "Nombre no puede ser null")
    @NotBlank(message = "Nombre no puede estar vacío")
    @Size(min = 2, max = 60, message = "Nombre debe ser entre 2 y 60 letras.")
    private String nombre;

    @Size(max = 255, message = "Descripcion debe ser menor igual a 255 letras.")
    private String descripcion;
}

