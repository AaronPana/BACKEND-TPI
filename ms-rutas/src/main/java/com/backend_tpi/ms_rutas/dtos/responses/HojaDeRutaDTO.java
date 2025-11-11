package com.backend_tpi.ms_rutas.dtos.responses;

import com.backend_tpi.ms_rutas.models.Tramo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HojaDeRutaDTO {
    private Long idTraslado;
    private int cantidadTramos;
    private List<Tramo> tramos;
}
