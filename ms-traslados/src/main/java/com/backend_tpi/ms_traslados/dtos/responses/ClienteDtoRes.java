package com.backend_tpi.ms_traslados.dtos.responses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ClienteDtoRes {

  @NotBlank
  private Long nroDocumento;

  @NotBlank
  private String nombre;

  private String apellido;

  private String telefono;

  private String email;

  private String direccion;

  private Long idCiudad;

  @NotNull
  private String tieneTraslados;
}
