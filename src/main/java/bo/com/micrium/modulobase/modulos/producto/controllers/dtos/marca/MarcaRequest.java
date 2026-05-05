package bo.com.micrium.modulobase.modulos.producto.controllers.dtos.marca;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class MarcaRequest implements Serializable {

    @NotNull(message = "Nombre no puede ser null")
    @NotBlank(message = "Nombre no puede estar vacío")
    @Size(min = 2, max = 60, message = "Nombre debe ser entre 2 y 60 letras.")
    private String nombre;

    @Size(max = 255, message = "Descripcion debe ser menor igual a 255 letras.")
    private String descripcion;
}
