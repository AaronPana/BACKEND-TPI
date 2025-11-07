package com.backend_tpi.ms_traslados.dtos.responses;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class FullClienteDtoRes {

  @NotBlank
  private Long nroDocumento;

  @NotBlank
  private String nombreCompleto;

  private String telefono;

  private String email;

  private String direccion;

  private String ciudadProvincia;

  private List<TrasladoSinClienteDtoRes> traslados;
}
