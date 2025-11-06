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
    private Long idDeposito;

    @Column(name = "NOMBRE", length = 200)
    private String nombre;

    @Column(name = "COSTO_X_DIA", columnDefinition= "DECIMAL(12,2)")
    private Double costoXdia;

    @Column(name = "LATITUD", columnDefinition= "DECIMAL(12,2)")
    private Double latitud;

    @Column(name = "LONGITUD", columnDefinition= "DECIMAL(12,2)")
    private Double longitud;

    @Column(name = "DIRECCION", length = 300)
    private String direccion;

    @Column(name = "ID_CIUDAD")
    private Long idCiudad;

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
