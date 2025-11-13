package com.backend_tpi.ms_rutas.dtos.responses;

import lombok.Data;

@Data
public class RutaSeleccionadaDTO {
    private String direccionOrigen;
    private String direccionDestino;
    private Double costoEstimado;
    private String tipoTramo;

    private String patenteCamion;
    private Long legajoTransportista;

    private Long idDepositoOrigen;
    private Long idDepositoDestino;

    private Long idTraslado;
    private Long idCiudadOrigen;
    private Long idCiudadDestino;
}