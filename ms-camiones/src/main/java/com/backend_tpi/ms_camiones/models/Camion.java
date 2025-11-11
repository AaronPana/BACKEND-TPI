package com.backend_tpi.ms_camiones.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "CAMIONES")
@Getter @Setter
public class Camion {

    @Id
    @Column(name = "PATENTE", length = 20)
    private String patente;

    @Column(name = "CAPACIDAD_PESO", nullable = false)
    private Double capacidadPeso;

    @Column(name = "CAPACIDAD_VOLUMEN", nullable = false)
    private Double capacidadVolumen;

    @Column(name = "CONSUMO_PROMEDIO", nullable = false)
    private Double consumoPromedio;

    // 'S' o 'N' (en BD: CHAR(1))
    @Column(name = "ESTA_DISPONIBLE", nullable = false, length = 1)
    private String estaDisponible;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_TARIFA", nullable = false)
    private Tarifa tarifa;
}
