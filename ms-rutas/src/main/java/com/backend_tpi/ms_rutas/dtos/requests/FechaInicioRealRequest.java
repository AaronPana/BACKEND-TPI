package com.backend_tpi.ms_rutas.dtos.requests;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class FechaInicioRealRequest {
    private LocalDateTime fechaInicioReal;
}
