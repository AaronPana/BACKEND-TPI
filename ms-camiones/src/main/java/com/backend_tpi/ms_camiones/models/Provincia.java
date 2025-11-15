package com.backend_tpi.ms_camiones.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "PROVINCIAS")
@Getter
@Setter
public class Provincia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROVINCIA")
    private Long idProvincia;

    @Column(name = "NOMBRE", nullable = false, unique = true, length = 80)
    private String nombre;

    // Relación uno a muchos con CIUDADES
    @OneToMany(mappedBy = "provincia", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("provincia")
    private List<Ciudad> ciudades;
}
