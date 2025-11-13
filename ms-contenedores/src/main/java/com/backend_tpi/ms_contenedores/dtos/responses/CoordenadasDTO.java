package com.backend_tpi.ms_contenedores.dtos.responses;


public class CoordenadasDTO {

    private Double latitud;
    private Double longitud;

    public CoordenadasDTO() {
    }

    public CoordenadasDTO(Double latitud, Double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}
