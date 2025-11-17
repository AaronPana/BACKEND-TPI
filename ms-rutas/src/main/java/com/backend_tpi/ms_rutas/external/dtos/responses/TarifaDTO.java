package com.backend_tpi.ms_rutas.external.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TarifaDTO {
    private Long idTarifa;
    private String concepto;
    private Double costoXKilometro;
}