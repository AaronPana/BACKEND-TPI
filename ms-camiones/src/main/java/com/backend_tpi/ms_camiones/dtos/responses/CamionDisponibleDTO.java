package com.backend_tpi.ms_camiones.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CamionDisponibleDTO {
    private String patente;
    private double capacidadPeso;
    private double capacidadVolumen;
    private double consumoPromedio;
    private double costoXKilometro; // de la Tarifa
}
