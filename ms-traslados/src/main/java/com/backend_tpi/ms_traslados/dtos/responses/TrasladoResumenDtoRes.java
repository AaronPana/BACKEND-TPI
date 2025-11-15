package com.backend_tpi.ms_traslados.dtos.responses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrasladoResumenDtoRes {

  @NotNull
  private Long idTraslado;
  private LocalDateTime fechaInicioTraslado;
  private LocalDateTime fechaFinTraslado;

  @NotBlank
  private String direccionOrigen;

  @NotBlank
  private String direccionDestino;

  @NotNull
  private Long idCiudadOrigen;

  @NotNull
  private Long idCiudadDestino;
}
