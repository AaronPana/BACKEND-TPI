package com.backend_tpi.ms_rutas.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TramoRequestDTO {
    private Long idTraslado;
    private String idConsulta;   // opcional: si ya existe (cuando viene selección)
    private String idRuta;       // opcional: si ya existe (cuando viene selección)

    private Long idCiudadOrigen;
    private Long idCiudadDestino;

    private String direccionOrigen;  // provistas por ms-traslados (strings)
    private String direccionDestino;

    private Double pesoContenedor;
    private Double volumenContenedor;
    private Long idContenedor;
    private Integer maximosDepositos = 3; // al generar alternativas
}
