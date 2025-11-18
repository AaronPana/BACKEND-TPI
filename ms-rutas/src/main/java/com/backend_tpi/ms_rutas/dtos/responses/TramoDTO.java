package com.backend_tpi.ms_rutas.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TramoDTO {
    private Long idTramo;
    private String direccionOrigen;
    private String direccionDestino;
    private LocalDateTime fechaInicioEstimada;
    private LocalDateTime fechaFinEstimada;
    private LocalDateTime fechaInicioReal;
    private LocalDateTime fechaFinReal;
    private String estadoTramo;
    private String tipoTramo;
    private String patenteCamion;
    private String legajoTransportista;
    private Long idTraslado;
}

