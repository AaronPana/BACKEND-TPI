package com.backend_tpi.ms_traslados.dtos.requests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrasladoPostDtoReq {

  @NotNull(message = "El número de documento no puede estar vacío")
  @Positive(message = "El número de documento debe ser positivo")
  @Min(value = 10000000, message = "El número de documento debe tener 8 dígitos")
  @Max(value = 99999999, message = "El número de documento debe tener 8 dígitos")
  private Long nroDocumento;

  @NotBlank(message = "La direccion origen no puede estar vacia")
  private String direccionOrigen;

  @NotBlank(message = "La direccion destino no puede estar vacia")
  private String direccionDestino;

  @NotNull(message = "Se debe proporcionar un id de ciudad")
  private Long idCiudadOrigen;

  @NotNull(message = "Se debe proporcionar un id de ciudad")
  private Long idCiudadDestino;
}
