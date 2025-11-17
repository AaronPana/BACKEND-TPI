package com.backend_tpi.ms_contenedores.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FechaFinRealDTO {
    private LocalDateTime fechaHoraFinReal;
}
