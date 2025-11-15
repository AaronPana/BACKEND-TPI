package com.backend_tpi.ms_camiones.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TarifaDTO {

    private Integer idTarifa;
    private String concepto;
    private Double costoXKilometro;
}
