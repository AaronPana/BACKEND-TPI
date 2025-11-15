package com.backend_tpi.ms_camiones.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CamionFiltradoDTO {

    private String patente;
    private Double capacidadPeso;
    private Double capacidadVolumen;
    private Double consumoPromedio;
}
