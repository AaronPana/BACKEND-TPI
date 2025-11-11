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
public class Tramo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTramo;

    @Column(name = "FECHA_HORA_INICIO_ESTIMADA")
    private LocalDateTime fechaHoraInicioEstimada;

    @Column(name = "FECHA_HORA_FIN_ESTIMADA")
    private LocalDateTime fechaHoraFinEstimada;

    @Column(name = "FECHA_HORA_INICIO_REAL")
    private LocalDateTime fechaHoraInicioReal;

    @Column(name = "FECHA_HORA_FIN_REAL")
    private LocalDateTime fechaHoraFinReal;

    @Column(name = "COSTO_ESTIMADO", columnDefinition = "DECIMAL(12,2)")
    private Double costoEstimado;

    @Column(name = "COSTO_REAL", columnDefinition = "DECIMAL(12,2)")
    private Double costoReal;

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
    private Long legajoTransportista;

    @Column(name = "ID_DEPOSITO_ORIGEN")
    private Long idDepositoOrigen;

    @Column(name = "ID_DEPOSITO_DESTINO")
    private Long idDepositoDestino;

    @Column(name = "ID_TRASLADO",  nullable = false)
    private Long idTraslado;

    @Column(name = "ID_CIUDAD_ORIGEN", nullable = false)
    private Long idCiudadOrigen;

    @Column(name = "ID_CIUDAD_DESTINO", nullable = false)
    private Long idCiudadDestino;


    public Tramo(Long idTramo, LocalDateTime fechaHoraInicioEstimada, LocalDateTime fechaHoraFinEstimada, LocalDateTime fechaHoraInicioReal, LocalDateTime fechaHoraFinReal, Double costoEstimado, Double costoReal, String direccionOrigen, String direccionDestino, EstadoTramo estadoTramo, TipoTramo tipoTramo, String patenteCamion, Long legajoTransportista, Long idDepositoOrigen, Long idDepositoDestino, Long idTraslado, Long idCiudadOrigen, Long idCiudadDestino) {
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