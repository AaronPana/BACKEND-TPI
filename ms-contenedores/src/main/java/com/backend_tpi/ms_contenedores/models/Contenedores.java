package com.backend_tpi.ms_contenedores.models;

import com.backend_tpi.ms_contenedores.constants.EstadoContenedor;
import com.backend_tpi.ms_contenedores.constants.converters.EstadoContenedorConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CONTENEDORES")
@Data
@NoArgsConstructor
public class Contenedores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idContenedor;

    @Column(name = "PESO", precision = 12, scale = 2)
    private double peso;

    @Column(name = "VOLUMEN", precision = 12, scale = 2)
    private double volumen;

    @Column(name = "COSTO_X_PESO_X_VOLUMEN", precision = 12, scale = 2)
    private double costoXpesoXvolumen;

    @Column(name = "LATITUD", precision = 12, scale = 8)
    private double latitud;

    @Column(name = "LONGITUD", precision = 12, scale = 8)
    private double longitud;

    @Convert(converter = EstadoContenedorConverter.class)
    @Column(name = "ID_ESTADO_CONTENEDORES", nullable = false)
    private EstadoContenedor estadoContenedores;

    public Contenedores(long idContenedor, double peso, double volumen, double costoXpesoXvolumen, double latitud, double longitud, EstadoContenedor estadoContenedores) {
        this.idContenedor = idContenedor;
        this.peso = peso;
        this.volumen = volumen;
        this.costoXpesoXvolumen = costoXpesoXvolumen;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estadoContenedores = estadoContenedores;
    }
}