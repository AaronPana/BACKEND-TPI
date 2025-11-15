package com.backend_tpi.ms_traslados.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRASLADOS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Traslado {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_TRASLADO")
  private Long idTraslado;

  @Column(name = "FECHA_INICIO_TRASLADO")
  private LocalDateTime fechaInicioTraslado;

  @Column(name = "FECHA_FIN_TRASLADO")
  private LocalDateTime fechaFinTraslado;

  @Column(name = "COSTO_ESTIMADO", columnDefinition = "DECIMAL(12,2)")
  private Double costoEstimado;

  @Column(name = "COSTO_REAL", columnDefinition = "DECIMAL(12,2)")
  private Double costoReal;

  @Column(name = "TIEMPO_ESTIMADO")
  private Long tiempoEstimadoMinutos;

  @Column(name = "TIEMPO_REAL")
  private Long tiempoRealMinutos;

  @Transient
  private Duration tiempoEstimado;

  @Transient
  private Duration tiempoReal;

  @Column(name = "DIRECCION_ORIGEN", length = 200)
  private String direccionOrigen;

  @Column(name = "DIRECCION_DESTINO", length = 200)
  private String direccionDestino;

  @Column(name = "ID_CIUDAD_ORIGEN", nullable = false)
  private Long idCiudadOrigen;

  @Column(name = "ID_CIUDAD_DESTINO", nullable = false)
  private Long idCiudadDestino;

  @Column(name = "ID_CONTENEDOR", nullable = false)
  private Long idContenedor;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "NRO_DOCUMENTO_CLIENTE", nullable = false, foreignKey = @ForeignKey(name = "FK_TRASLADOS_CLIENTE"))
  private Cliente cliente;

  public Duration getTiempoEstimado() {
    return this.tiempoEstimadoMinutos != null ? Duration.ofMinutes(this.tiempoEstimadoMinutos) : null;
  }

  public void setTiempoEstimado(Duration tiempoEstimado) {
    this.tiempoEstimado = tiempoEstimado;
    this.tiempoEstimadoMinutos = tiempoEstimado != null ? tiempoEstimado.toMinutes() : null;
  }

  public Duration getTiempoReal() {
    return this.tiempoRealMinutos != null ? Duration.ofSeconds(this.tiempoRealMinutos) : null;
  }

  public void setTiempoReal(Duration tiempoReal) {
    this.tiempoReal = tiempoReal;
    this.tiempoRealMinutos = tiempoReal != null ? tiempoReal.toMinutes() : null;
  }
}
