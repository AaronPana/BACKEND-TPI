package com.backend_tpi.ms_contenedores.dtos.responses;

public class PesoVolumenDTO {
    private double peso;
    private double volumen;

    public PesoVolumenDTO() {}

    public PesoVolumenDTO(double peso, double volumen) {
        this.peso = peso;
        this.volumen = volumen;
    }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    public double getVolumen() { return volumen; }
    public void setVolumen(double volumen) { this.volumen = volumen; }
}