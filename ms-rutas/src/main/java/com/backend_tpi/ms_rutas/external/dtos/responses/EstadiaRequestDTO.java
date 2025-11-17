package com.backend_tpi.ms_rutas.external.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadiaRequestDTO {
    private Long idContenedor;
    private Long idTraslado;
    private Long idDeposito;
    private LocalDateTime fechaInicio;
}
