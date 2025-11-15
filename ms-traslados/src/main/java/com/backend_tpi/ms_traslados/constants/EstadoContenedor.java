package com.backend_tpi.ms_traslados.constants;

import java.util.Objects;

public enum EstadoContenedor {
  ASIGNADO(1),
  EN_VIAJE(2),
  EN_DEPOSITO(3),
  ENTREGADO(4);

  private final Integer idEstadoContenedor;

  EstadoContenedor(Integer idEstadoContenedor) {
    this.idEstadoContenedor = idEstadoContenedor;
  }

  public Integer getId() {
    return this.idEstadoContenedor;
  }

  public static EstadoContenedor fromId(Integer idEstadoContenedor) {
    for (EstadoContenedor estado : values()) {
      if (Objects.equals(estado.getId(), idEstadoContenedor)) {
        return estado;
      }
    }
    throw new IllegalArgumentException("ID de estado contenedor inválido: " + idEstadoContenedor);
  }
}
