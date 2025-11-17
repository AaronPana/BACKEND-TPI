package com.backend_tpi.ms_locations.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RutaAlternativaDtoRes {

  private String idRuta; // ID único de esta ruta
  private List<String> path; // ["A", "D1", "D2", "B"]
  private Double distanciaTotal; // en metros
  private Double duracionTotal; // en segundos
  private List<DepositoDtoRes> depositos; // depósitos incluidos
}
