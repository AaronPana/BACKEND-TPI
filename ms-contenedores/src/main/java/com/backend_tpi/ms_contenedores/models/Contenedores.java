package com.backend_tpi.ms_contenedores.models;

import com.backend_tpi.ms_contenedores.constants.EstadoContenedor;
import com.backend_tpi.ms_contenedores.constants.converters.EstadoContenedorConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CONTENEDORES")
@Data
@NoArgsConstructor
public class Contenedores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContenedor;

    @Column(name = "PESO", columnDefinition= "DECIMAL(12,2)")
    private Double peso;

    @Column(name = "VOLUMEN", columnDefinition= "DECIMAL(12,2)")
    private Double volumen;

    @Column(name = "COSTO_X_PESO_X_VOLUMEN", columnDefinition= "DECIMAL(12,2)")
    private Double costoXpesoXvolumen;

    @Column(name = "LATITUD", columnDefinition= "DECIMAL(12,2)")
    private Double latitud;

    @Column(name = "LONGITUD", columnDefinition= "DECIMAL(12,2)")
    private Double longitud;

    @Convert(converter = EstadoContenedorConverter.class)
    @Column(name = "ID_ESTADO_CONTENEDOR", nullable = false)
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