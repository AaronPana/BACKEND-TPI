package com.backend_tpi.ms_rutas.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TramoDetalleDTO {
    private Long idTramo;
    private String direccionOrigen;
    private String direccionDestino;
    private LocalDateTime fechaInicioEstimada;
    private LocalDateTime fechaFinEstimada;
    private LocalDateTime fechaInicioReal;
    private LocalDateTime fechaFinReal;
    private Double costoEstimado;
    private Double costoReal;
    private String estadoTramo;
    private String tipoTramo;
    private String patenteCamion;
}
