package com.backend_tpi.ms_traslados.dtos.responses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class FullClienteDtoRes {

  @NotNull
  private Long nroDocumento;

  @NotBlank
  private String nombreCompleto;

  private String telefono;

  private String email;

  private String direccion;

  private String ciudadProvincia;

  private List<TrasladoResumenDtoRes> traslados;
}
