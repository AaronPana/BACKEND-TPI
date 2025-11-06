package com.backend_tpi.ms_camiones.models;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "CIUDADES")
@Data
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CIUDAD")
    private Long idCiudad;

    @Column(name = "NOMBRE", nullable = false, length = 80)
    private String nombre;

    // Relación muchos a uno con PROVINCIAS
    @ManyToOne
    @JoinColumn(name = "ID_PROVINCIA", nullable = false)
    private Provincia provincia;
}
