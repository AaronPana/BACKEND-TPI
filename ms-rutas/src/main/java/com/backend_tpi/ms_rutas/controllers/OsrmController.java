package com.backend_tpi.ms_rutas.controllers;

import com.backend_tpi.ms_rutas.dtos.responses.RutaAlternativaDTO;
import com.backend_tpi.ms_rutas.dtos.responses.RutaSeleccionadaDTO;
import com.backend_tpi.ms_rutas.models.Tramo;
import com.backend_tpi.ms_rutas.services.OsrmService;
import com.backend_tpi.ms_rutas.services.TramoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/osrm")
public class OsrmController {

    private final OsrmService osrmService;
    private final TramoService tramoService;

    public OsrmController(OsrmService osrmService, TramoService tramoService) {
        this.osrmService = osrmService;
        this.tramoService = tramoService;
    }

    // Obtiene rutas alternativas (usando coordenadas)
    @GetMapping("/alternativas")
    public List<RutaAlternativaDTO> obtenerAlternativas(
            @RequestParam double origenLat,
            @RequestParam double origenLon,
            @RequestParam double destinoLat,
            @RequestParam double destinoLon) {
        return osrmService.obtenerRutasAlternativas(origenLat, origenLon, destinoLat, destinoLon);
    }

    // Guarda solo la seleccionada (sin coordenadas)
    @PostMapping("/seleccionada")
    public Tramo guardarRutaSeleccionada(@RequestBody RutaSeleccionadaDTO dto) {
        return tramoService.guardarRutaSeleccionada(dto);
    }
}

