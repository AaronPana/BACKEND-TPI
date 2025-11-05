package com.backend_tpi.ms_traslados.constants;

import java.util.Objects;

public enum EstadoTraslado {
  SOLICITADO(1),
  PROGRAMADO(2),
  EN_TRANSITO(3),
  FINALIZADO(4);

  private final Integer idEstadoTramo;

  EstadoTraslado(Integer idEstadoTramo) {
    this.idEstadoTramo = idEstadoTramo;
  }

  public Integer getId() {
    return this.idEstadoTramo;
  }

  public static EstadoTraslado fromId(Integer idEstadoTramo) {
    for (EstadoTraslado estado : values()) {
      if (Objects.equals(estado.getId(), idEstadoTramo)) {
        return estado;
      }
    }
    throw new IllegalArgumentException("ID de estado tramo invalido: " + idEstadoTramo);
  }
}
