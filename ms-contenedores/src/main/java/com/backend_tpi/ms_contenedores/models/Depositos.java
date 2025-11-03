package com.backend_tpi.ms_contenedores.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DEPOSITOS")
@Data
@NoArgsConstructor
public class Depositos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDeposito;

    @Column(name = "NOMBRE", length = 200)
    private String nombre;

    @Column(name = "COSTO_X_DIA", precision = 12, scale = 2)
    private double costoXdia;

    @Column(name = "LATITUD", precision = 12, scale = 8)
    private double latitud;

    @Column(name = "LONGITUD", precision = 12, scale = 8)
    private double longitud;

    @Column(name = "DIRECCION", length = 300)
    private String direccion;

    @Column(name = "ID_CIUDAD")
    private long idCiudad;

    public Depositos(long idDeposito, String nombre, double costoXdia, double latitud, double longitud, String direccion, long idCiudad) {
        this.idDeposito = idDeposito;
        this.nombre = nombre;
        this.costoXdia = costoXdia;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
        this.idCiudad = idCiudad;
    }
}
