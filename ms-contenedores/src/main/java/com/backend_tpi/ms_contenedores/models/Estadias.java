package com.backend_tpi.ms_contenedores.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ESTADIAS")
@Data
@NoArgsConstructor
public class Estadias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idEstadia;

    @Column(name = "FECHA_HORA_INICIO_ESTIMADA")
    private LocalDateTime fechaHoraInicioEstimada;

    @Column(name = "FECHA_HORA_FIN_ESTIMADA")
    private LocalDateTime fechaHoraFinEstimada;

    @Column(name = "FECHA_HORA_INICIO_REAL")
    private LocalDateTime fechaHoraInicioReal;

    @Column(name = "FECHA_HORA_FIN_REAL")
    private LocalDateTime fechaHoraFinReal;

    @Column(name = "COSTO_ESTIMADO", precision = 12, scale = 2)
    private double costoEstimado;

    @Column(name = "COSTO_REAL", precision = 12, scale = 2)
    private double costoReal;

    @Column(name = "ID_DEPOSITO")
    private long idDeposito;

    @Column(name = "ID_CONTENEDOR")
    private long idContenedor;

    @Column(name = "ID_TRASLADO")
    private long idTraslado;

    public Estadias(long idEstadia, LocalDateTime fechaHoraInicioEstimada, LocalDateTime fechaHoraFinEstimada,
                    LocalDateTime fechaHoraInicioReal, LocalDateTime fechaHoraFinReal,
                    double costoEstimado, double costoReal, long idDeposito, long idContenedor, long idTraslado) {
        this.idEstadia = idEstadia;
        this.fechaHoraInicioEstimada = fechaHoraInicioEstimada;
        this.fechaHoraFinEstimada = fechaHoraFinEstimada;
        this.fechaHoraInicioReal = fechaHoraInicioReal;
        this.fechaHoraFinReal = fechaHoraFinReal;
        this.costoEstimado = costoEstimado;
        this.costoReal = costoReal;
        this.idDeposito = idDeposito;
        this.idContenedor = idContenedor;
        this.idTraslado = idTraslado;
    }
}