package com.backend_tpi.ms_traslados.dtos.responses;

import java.time.LocalDateTime;

public interface TrasladoSinClienteDtoRes {

  Long getIdTraslado();
  LocalDateTime getFechaInicioTraslado();
  LocalDateTime getFechaFinTraslado();
  Double getCostoEstimado();
  Double getCostoReal();
  Integer getTiempoEstimado();
  Integer getTiempoReal();
  String getDireccionOrigen();
  String getDireccionDestino();
  Long getIdCiudadOrigen();
  Long getIdCiudadDestino();
  Long getIdContenedor();
}
