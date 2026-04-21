package bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.cliente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class ClienteRequest implements Serializable {

    private Long id;

    @NotNull(message = "CI no puede ser null")
    @NotBlank(message = "El CI no puede estar vacío")
    @Size(max = 20, message = "CI debe ser maximo de 20 letras.")
    private String ci;

    @NotNull(message = "Nombre no puede ser null")
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 60, message = "Nombre debe ser maximo de 60 letras.")
    private String nombre;

    private String celular;
}
