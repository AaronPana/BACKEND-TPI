package com.backend_tpi.ms_rutas.external.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CamionDTO {
    private String patente;
    private Double capacidadPeso;
    private Double capacidadVolumen;
    private Double consumoPromedio;

}
