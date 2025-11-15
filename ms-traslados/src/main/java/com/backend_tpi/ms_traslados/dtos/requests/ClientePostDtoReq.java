package com.backend_tpi.ms_traslados.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientePostDtoReq {

  @NotNull(message = "El número de documento no puede estar vacío")
  @Positive(message = "El número de documento debe ser positivo")
  @Min(value = 10000000, message = "El número de documento debe tener 8 dígitos")
  @Max(value = 99999999, message = "El número de documento debe tener 8 dígitos")
  private Long nroDocumento;

  @NotBlank(message = "El nombre no puede estar vacío")
  @Size(max = 80, message = "El nombre es demasiado largo")
  private String nombre;

  @Size(max = 80, message = "El apellido es demasiado largo")
  private String apellido;

  @Size(max = 80, message = "El telefono es demasiado largo")
  private String telefono;

  @Email
  @Size(max = 100, message = "El email es demasiado largo")
  private String email;

  @Size(max = 200, message = "La direccion es demasiado larga")
  private String direccion;

  @NotNull(message = "Se debe proporcionar un id de ciudad")
  private Long idCiudad;
}
