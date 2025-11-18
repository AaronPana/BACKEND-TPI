package com.backend_tpi.ms_traslados.external.dtos.requests;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PesoVolumenDtoReq {

  @NotNull(message = "Debe enviarse el peso")
  @DecimalMin(value = "0.0", inclusive = false, message = "El peso debe ser mayor a 0")
  private Double peso;

  @NotNull(message = "Debe enviarse el volumen")
  @DecimalMin(value = "0.0", inclusive = false, message = "El volumen debe ser mayor a 0")
  private Double volumen;
}
