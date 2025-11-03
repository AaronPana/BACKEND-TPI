package com.backend_tpi.ms_contenedores.constants;

public enum EstadoContenedor {

    DISPONIBLE(1),
    EN_TRANSITO(2),
    EN_REPARACION(3),
    EN_DEPOSITO(4),
    FUERA_DE_SERVICIO(5);

    private final int id;

    EstadoContenedor(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // Método para obtener el enum a partir del ID
    public static EstadoContenedor fromId(int id) {
        for (EstadoContenedor estado : values()) {
            if (estado.getId() == id) {
                return estado;
            }
        }
        throw new IllegalArgumentException("ID de estado de contenedor inválido: " + id);
    }
}