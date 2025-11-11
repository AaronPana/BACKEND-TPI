package com.backend_tpi.ms_camiones.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TRANSPORTISTAS")
@Getter @Setter
public class Transportista {

    @Id
    @Column(name = "LEGAJO")
    private Integer legajo;

    @Column(name = "NOMBRE", nullable = false, length = 80)
    private String nombre;

    @Column(name = "APELLIDO", length = 80)
    private String apellido;

    @Column(name = "TELEFONO", length = 20)
    private String telefono;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @Column(name = "DIRECCION", length = 200)
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "ID_CIUDAD")   // FK a CIUDADES
    private Ciudad ciudad;
}

