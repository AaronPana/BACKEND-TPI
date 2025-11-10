package com.backend_tpi.ms_camiones.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "TARIFAS")
@Getter @Setter
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TARIFA")
    private Integer idTarifa;

    @Column(name = "CONCEPTO", nullable = false, length = 100, unique = true)
    private String concepto;

    @Column(name = "COSTO_X_KILOMETRO", nullable = false)
    private Double costoXKilometro;
}
