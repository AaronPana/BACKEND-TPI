package com.backend_tpi.ms_rutas.constants;

public enum TipoTramo {

    ORIGEN_DESTINO(1),
    ORIGEN_DEPOSITO(2),
    DEPOSITO_DEPOSITO(3),
    DEPOSITO_DESTINO(4);

    private final int idTipoTramo;

    TipoTramo(int id) {
        this.idTipoTramo = id;
    }

    public int getId() {
        return idTipoTramo;
    }

    // Método para buscar el enum a partir del ID
    public static TipoTramo fromId(int idTipoTramo) {
        for (TipoTramo tipo : values()) {
            if (tipo.getId() == idTipoTramo) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("ID de tipo tramo inválido: " + idTipoTramo);
    }
}
