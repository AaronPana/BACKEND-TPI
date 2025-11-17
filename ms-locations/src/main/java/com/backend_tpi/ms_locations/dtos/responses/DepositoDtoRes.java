package com.backend_tpi.ms_locations.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositoDtoRes {

  private Long idDeposito;
  private String nombre;
  private Double latitud;
  private Double longitud;
  private Double distanciaDesdePuntoMedioRuta; // distancia desde el punto medio de la ruta
}
