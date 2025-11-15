package com.backend_tpi.ms_traslados.dtos.responses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ClienteDtoRes {

  @NotNull
  private Long nroDocumento;

  @NotNull
  @NotBlank
  private String nombreCompleto;

  private String telefono;

  private String ciudadProvincia;

  @NotBlank
  private String tieneTraslados;
}
