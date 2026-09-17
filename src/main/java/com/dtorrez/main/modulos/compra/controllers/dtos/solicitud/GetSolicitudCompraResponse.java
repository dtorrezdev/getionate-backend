package com.dtorrez.main.modulos.compra.controllers.dtos.solicitud;

import com.dtorrez.main.modulos.compra.controllers.dtos.orden_compra.get.GetDetalleCompraResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class GetSolicitudCompraResponse implements Serializable {
    private Long id;
    private String codigo;
    private BigDecimal total;
    private Timestamp fecha;
    private String estado;
    private String glosa;
    private List<GetDetalleCompraResponse> detalle;
}
