package com.backend_tpi.ms_rutas.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TramoRequestDTO {
    private String direccionOrigen;
    private Long idCiudadOrigen;
    private String direccionDestino;
    private Long idCiudadDestino;
    private Long idTraslado;
    private Long legajoTransportista;
    private Double pesoContenedor;
    private Double volumenContenedor;
    private Long idDepositoOrigen;
    private Long idDepositoDestino;
    private Long idContenedor;
}
