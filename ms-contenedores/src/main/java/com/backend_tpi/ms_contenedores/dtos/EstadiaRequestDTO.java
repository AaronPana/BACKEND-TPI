package com.backend_tpi.ms_contenedores.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadiaRequestDTO {
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private Long idContenedor;
    private Long idTraslado;
    private Long idDeposito;
}
