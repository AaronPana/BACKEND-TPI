package com.backend_tpi.ms_locations.external.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositoAlternativoDtoRes {

  private Long idDeposito;
  private String nombre;
  private Double costoXdia;
  private Double latitud;
  private Double longitud;
  private String direccion;
  private Long idCiudad;
}
