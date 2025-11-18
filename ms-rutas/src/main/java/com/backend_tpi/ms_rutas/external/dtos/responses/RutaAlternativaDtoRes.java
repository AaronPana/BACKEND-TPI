package com.backend_tpi.ms_rutas.external.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RutaAlternativaDtoRes {
    private String idRuta;
    private List<String> path; // opcional: ["A","D1","B"]
    private Double distanciaTotal;
    private Double duracionTotal;
    private List<DepositoDtoRes> depositos; // si aplica
    private List<TramoRutaDTO> tramos; // depósitos incluidos
}
