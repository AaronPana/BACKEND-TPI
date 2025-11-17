package com.backend_tpi.ms_locations.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RutaDtoReq {

  @NotNull(message = "La latitud de origen es obligatoria")
  @DecimalMin(value = "-90.0", message = "La latitud de origen debe estar entre -90 y 90")
  @DecimalMax(value = "90.0", message = "La latitud de origen debe estar entre -90 y 90")
  private Double latitudOrigen;

  @NotNull(message = "La longitud de origen es obligatoria")
  @DecimalMin(value = "-180.0", message = "La longitud de origen debe estar entre -180 y 180")
  @DecimalMax(value = "180.0", message = "La longitud de origen debe estar entre -180 y 180")
  private Double longitudOrigen;

  @NotNull(message = "La latitud de destino es obligatoria")
  @DecimalMin(value = "-90.0", message = "La latitud de destino debe estar entre -90 y 90")
  @DecimalMax(value = "90.0", message = "La latitud de destino debe estar entre -90 y 90")
  private Double latitudDestino;

  @NotNull(message = "La longitud de destino es obligatoria")
  @DecimalMin(value = "-180.0", message = "La longitud de destino debe estar entre -180 y 180")
  @DecimalMax(value = "180.0", message = "La longitud de destino debe estar entre -180 y 180")
  private Double longitudDestino;

  @Min(value = 0, message = "El máximo de depósitos no puede ser negativo")
  @Max(value = 10, message = "El máximo de depósitos no puede exceder 10")
  private Integer maximosDepositos = 3; // máximo de depósitos a considerar
}
