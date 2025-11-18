package com.backend_tpi.ms_rutas.external.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TramoRutaDTO {
    private String direccionOrigen;
    private String direccionDestino;
    private Long idDepositoOrigen;      // nullable
    private Long idDepositoDestino;     // nullable
    private Long idCiudadOrigen;        // nullable (si ms-location las conoce)
    private Long idCiudadDestino;       // nullable
    private Double distanciaMeters;     // distancia del segmento (m)
    private Double duracionSeconds;     // duración del segmento (s)
    private Double pesoContenedor;      // opcional, para buscar camión
    private Double volumenContenedor;
}
