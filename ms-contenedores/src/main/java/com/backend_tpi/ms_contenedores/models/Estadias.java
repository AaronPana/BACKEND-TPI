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
    private Long idEstadia;

    @Column(name = "FECHA_HORA_INICIO_ESTIMADA")
    private LocalDateTime fechaHoraInicioEstimada;

    @Column(name = "FECHA_HORA_FIN_ESTIMADA")
    private LocalDateTime fechaHoraFinEstimada;

    @Column(name = "FECHA_HORA_INICIO_REAL")
    private LocalDateTime fechaHoraInicioReal;

    @Column(name = "FECHA_HORA_FIN_REAL")
    private LocalDateTime fechaHoraFinReal;

    @Column(name = "COSTO_ESTIMADO", columnDefinition= "DECIMAL(12,2)")
    private Double costoEstimado;

    @Column(name = "COSTO_REAL", columnDefinition= "DECIMAL(12,2)")
    private Double costoReal;

    @Column(name = "ID_DEPOSITO")
    private Long idDeposito;

    @Column(name = "ID_CONTENEDOR")
    private Long idContenedor;

    @Column(name = "ID_TRASLADO")
    private Long idTraslado;

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