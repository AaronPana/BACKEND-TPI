package com.backend_tpi.ms_traslados.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrasladoPatchDtoReq {

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime fechaInicioTraslado;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime fechaFinTraslado;

  @DecimalMin(value = "0.0", inclusive = false, message = "El costo estimado debe ser mayor a 0")
  private Double costoEstimado;

  @DecimalMin(value = "0.0", inclusive = false, message = "El costo real debe ser mayor a 0")
  private Double costoReal;

  @JsonFormat(pattern = "dd HH:mm:ss")
  @Pattern(regexp = "\\d{1,2} \\d{2}:\\d{2}:\\d{2}", message = "Formato inválido. Use 'dd HH:mm:ss'. Ej: '01 02:30:00'")
  private String tiempoEstimado;

  @JsonFormat(pattern = "dd HH:mm:ss")
  @Pattern(regexp = "\\d{1,2} \\d{2}:\\d{2}:\\d{2}", message = "Formato inválido. Use 'dd HH:mm:ss'. Ej: '00 01:45:30'")
  private String tiempoReal;
}
