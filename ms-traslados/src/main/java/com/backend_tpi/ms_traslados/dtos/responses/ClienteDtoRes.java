package com.backend_tpi.ms_traslados.dtos.responses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ClienteDtoRes {

  @NotBlank
  private Long nroDocumento;

  @NotBlank
  private String nombreCompleto;

  private String telefono;

  private String ciudadProvincia;

  @NotNull
  private String tieneTraslados;
}
