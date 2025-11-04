package com.backend_tpi.ms_traslados.constants;

public enum EstadoTraslado {
  SOLICITADO(1),
  PROGRAMADO(2),
  EN_TRANSITO(3),
  FINALIZADO(4);

  private final int idEstadoTramo;

  EstadoTraslado(int idEstadoTramo) {
    this.idEstadoTramo = idEstadoTramo;
  }

  public int getId() {
    return this.idEstadoTramo;
  }

  public static EstadoTraslado fromId(int idEstadoTramo) {
    for (EstadoTraslado estado : values()) {
      if (estado.getId() == idEstadoTramo) {
        return estado;
      }
    }
    throw new IllegalArgumentException("ID de estado tramo invalido: " + idEstadoTramo);
  }
}
