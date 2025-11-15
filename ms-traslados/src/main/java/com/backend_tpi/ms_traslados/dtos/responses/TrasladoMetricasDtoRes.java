package com.backend_tpi.ms_traslados.dtos.responses;

import lombok.Data;

@Data
public class TrasladoMetricasDtoRes {

  private Double costoEstimado;
  private Double costoReal;
  private String tiempoEstimado;
  private String tiempoReal;
}
