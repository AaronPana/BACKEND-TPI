package com.backend_tpi.ms_camiones.dtos;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TarifaDTO {
    private Integer idTarifa;
    private String concepto;
    private Double costoXKilometro;
}
