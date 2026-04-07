package bo.com.micrium.modulobase.modulos.producto.controllers.dtos.producto_presentacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductoPresentacionResponse implements Serializable {
    private Long id;
}
