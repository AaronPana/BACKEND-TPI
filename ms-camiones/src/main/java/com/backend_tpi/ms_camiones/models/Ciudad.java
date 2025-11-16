package com.backend_tpi.ms_camiones.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CIUDADES")
@Getter
@Setter
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CIUDAD")
    private Long idCiudad;

    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Column(name = "LATITUD", columnDefinition= "DECIMAL(12,2)")
    private Double latitud;

    @Column(name = "LONGITUD", columnDefinition= "DECIMAL(12,2)")
    private Double longitud;
    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_PROVINCIA", nullable = false)
    @JsonIgnoreProperties("ciudades")  // cuando serializa provincia, NO incluye provincia.ciudades
    private Provincia provincia;
}

