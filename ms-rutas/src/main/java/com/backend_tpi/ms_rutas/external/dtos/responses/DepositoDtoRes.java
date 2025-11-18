package com.backend_tpi.ms_rutas.external.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DepositoDtoRes {
    private Long idDeposito;
    private String nombre;
    private Double latitud;
    private Double longitud;
    private Double distanciaDesdePuntoMedioRuta;
}
