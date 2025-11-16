package com.backend_tpi.ms_contenedores.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoordenadasDTO {
    @JsonAlias({"lat", "latitud"})
    private Double latitud;
    @JsonAlias({"lng", "longitud"})
    private Double longitud;

}
