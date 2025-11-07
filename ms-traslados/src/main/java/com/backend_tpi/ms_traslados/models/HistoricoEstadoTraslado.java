package com.backend_tpi.ms_traslados.models;

import com.backend_tpi.ms_traslados.constants.EstadoTraslado;
import com.backend_tpi.ms_traslados.constants.converters.EstadoTrasladoConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "HISTORICOS_ESTADOS_TRASLADOS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoEstadoTraslado {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_HISTORICO")
  private Long idHistorico;

  @Column(name = "FECHA_HORA_INICIO_ESTADO")
  private LocalDateTime fechaHoraInicioEstado;

  @Column(name = "FECHA_HORA_FIN_ESTADO")
  private LocalDateTime fechaHoraFinEstado;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ID_TRASLADO", nullable = false, foreignKey = @ForeignKey(name = "FK_HISTORICOS_TRASLADO"))
  private Traslado traslado;

  @Convert(converter = EstadoTrasladoConverter.class)
  @Column(name = "ID_ESTADO_TRASLADO", nullable = false)
  private EstadoTraslado estadoTraslado;
}
