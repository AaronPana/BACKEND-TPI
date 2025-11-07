package com.backend_tpi.ms_traslados.external.dtos.responses;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CiudadProvinciaDtoRes {

  @NotNull
  private String ciudadProvincia;
}
