package com.backend_tpi.ms_traslados.dtos.responses;


import com.backend_tpi.ms_traslados.external.dtos.responses.ContenedorDtoRes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrasladoDetalleDtoRes {

  @NotNull
  private Long idTraslado;
  private LocalDateTime fechaInicioTraslado;
  private LocalDateTime fechaFinTraslado;
  private Double costoEstimado;
  private Double costoReal;
  private String tiempoEstimado;
  private String tiempoReal;

  @NotBlank
  private String direccionOrigen;

  @NotBlank
  private String direccionDestino;

  @NotNull
  private Long idCiudadOrigen;

  @NotNull
  private Long idCiudadDestino;

  @NotNull
  private ContenedorDtoRes contenedor;
}
