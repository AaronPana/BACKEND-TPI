package com.backend_tpi.ms_rutas.models;

import com.backend_tpi.ms_rutas.constants.EstadoTramo;
import com.backend_tpi.ms_rutas.constants.TipoTramo;
import com.backend_tpi.ms_rutas.constants.converters.EstadoTramoConverter;
import com.backend_tpi.ms_rutas.constants.converters.TipoTramoConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TRAMOS")
@Data
@NoArgsConstructor
public class Tramos{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTramo;

    @Column(name = "FECHA_HORA_INICIO_ESTIMADA")
    private LocalDateTime fechaHoraInicioEstimada;

    @Column(name = "FECHA_HORA_FIN_ESTIMADA")
    private LocalDateTime fechaHoraFinEstimada;

    @Column(name = "FECHA_HORA_INICIO_REAL")
    private LocalDateTime fechaHoraInicioReal;

    @Column(name = "FECHA_HORA_FIN_REAL")
    private LocalDateTime fechaHoraFinReal;

    @Column(name = "COSTO_ESTIMADO",precision = 12, scale = 2)
    private double costoEstimado;

    @Column(name = "COSTO_REAL", precision = 12, scale = 2)
    private double costoReal;

    @Column(name = "DIRECCION_ORIGEN", length = 200)
    private String direccionOrigen;

    @Column(name = "DIRECCION_DESTINO", length = 200)
    private String direccionDestino;

    @Convert(converter = EstadoTramoConverter.class)
    @Column(name = "ID_ESTADO_TRAMO", nullable = false)
    private EstadoTramo estadoTramo;

    @Convert(converter = TipoTramoConverter.class)
    @Column(name = "ID_TIPO_TRAMO", nullable = false)
    private TipoTramo tipoTramo;

    @Column(name = "PATENTE_CAMION", length = 20, nullable = false)
    private String patenteCamion;

    @Column(name = "LEGAJO_TRANSPORTISTA", nullable = false)
    private long legajoTransportista;

    @Column(name = "ID_DEPOSITO_ORIGEN")
    private long idDepositoOrigen;

    @Column(name = "ID_DEPOSITO_DESTINO")
    private long idDepositoDestino;

    @Column(name = "ID_TRASLADO",  nullable = false)
    private long idTraslado;

    @Column(name = "ID_CIUDAD_ORIGEN", nullable = false)
    private long idCiudadOrigen;

    @Column(name = "ID_CIUDAD_DESTINO", nullable = false)
    private long idCiudadDestino;


    public Tramos(long idTramo, LocalDateTime fechaHoraInicioEstimada, LocalDateTime fechaHoraFinEstimada, LocalDateTime fechaHoraInicioReal, LocalDateTime fechaHoraFinReal, double costoEstimado, double costoReal, String direccionOrigen, String direccionDestino, EstadoTramo estadoTramo, TipoTramo tipoTramo, String patenteCamion, long legajoTransportista, long idDepositoOrigen, long idDepositoDestino, long idTraslado, long idCiudadOrigen, long idCiudadDestino) {
        this.idTramo = idTramo;
        this.fechaHoraInicioEstimada = fechaHoraInicioEstimada;
        this.fechaHoraFinEstimada = fechaHoraFinEstimada;
        this.fechaHoraInicioReal = fechaHoraInicioReal;
        this.fechaHoraFinReal = fechaHoraFinReal;
        this.costoEstimado = costoEstimado;
        this.costoReal = costoReal;
        this.direccionOrigen = direccionOrigen;
        this.direccionDestino = direccionDestino;
        this.estadoTramo = estadoTramo;
        this.tipoTramo = tipoTramo;
        this.patenteCamion = patenteCamion;
        this.legajoTransportista = legajoTransportista;
        this.idDepositoOrigen = idDepositoOrigen;
        this.idDepositoDestino = idDepositoDestino;
        this.idTraslado = idTraslado;
        this.idCiudadOrigen = idCiudadOrigen;
        this.idCiudadDestino = idCiudadDestino;
    }
}