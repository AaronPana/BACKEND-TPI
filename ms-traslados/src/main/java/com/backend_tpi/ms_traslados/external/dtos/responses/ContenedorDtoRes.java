package com.backend_tpi.ms_traslados.external.dtos.responses;

import com.backend_tpi.ms_traslados.constants.EstadoContenedor;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContenedorDtoRes {

  @NotNull
  private Long idContenedor;

  @NotNull
  private Double peso;

  @NotNull
  private Double volumen;

  @NotNull
  private Double costoXpesoXvolumen;

  @NotNull
  private Double latitud;

  @NotNull
  private Double longitud;

  @NotNull
  private EstadoContenedor estadoContenedores;
}
