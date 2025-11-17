package com.backend_tpi.ms_locations.external.dtos.responses;

import lombok.Data;

import java.util.List;

@Data
public class RutaOsrmDtoRes {

  private String code;
  private List<Ruta> routes;

  @Data
  public static class Ruta {
    private Double distance; // en metros
    private Double duration; // en segundos
  }
}
