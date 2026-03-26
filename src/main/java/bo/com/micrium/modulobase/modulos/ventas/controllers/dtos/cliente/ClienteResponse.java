package bo.com.micrium.modulobase.modulos.ventas.controllers.dtos.cliente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponse implements Serializable {
    private Long id;
    private String ci;
    private String nombre;
    private String celular;
}
