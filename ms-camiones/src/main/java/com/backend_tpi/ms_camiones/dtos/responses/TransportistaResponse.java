package com.backend_tpi.ms_camiones.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransportistaResponse {
    private Integer legajo;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String direccion;
}


