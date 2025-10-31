package com.backend_tpi.ms_rutas.constants;

public enum EstadoTramo {
    ASIGNADO(1),
    INICIADO(2),
    FINALIZADO(3);

    private final int idEstadoTramo;

    EstadoTramo(int id) {
        this.idEstadoTramo = id;
    }

    public int getId() {
        return idEstadoTramo;
    }

    public static EstadoTramo fromId(int idEstadoTramo) {
        for (EstadoTramo estado : values()) {
            if (estado.getId() == idEstadoTramo) {
                return estado;
            }
        }
        throw new IllegalArgumentException("ID de estado tramo inválido: " + idEstadoTramo);
    }
}
