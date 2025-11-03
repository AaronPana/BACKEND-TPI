package com.backend_tpi.ms_contenedores.constants;

public enum EstadoContenedor {

    ASIGNADO(1),
    EN_VIAJE(2),
    EN_DEPOSITO(3),
    ENTREGADO(4);

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