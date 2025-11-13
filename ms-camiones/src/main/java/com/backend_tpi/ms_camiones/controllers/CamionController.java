package com.backend_tpi.ms_camiones.controllers;

import com.backend_tpi.ms_camiones.dtos.responses.CamionFiltradoDTO;
import com.backend_tpi.ms_camiones.services.CamionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/camiones")
public class CamionController {

    @Autowired
    private CamionService camionService;

    // 🔹 GET /camiones/disponibles/patentes -> lista de patentes disponibles
    @GetMapping("/disponibles/patentes")
    public ResponseEntity<List<String>> patentesDisponibles() {
        return ResponseEntity.ok(camionService.obtenerPatentesDisponibles());
    }

    // 🔹 GET /camiones/no-disponibles/patentes -> lista de patentes NO disponibles
    @GetMapping("/no-disponibles/patentes")
    public ResponseEntity<List<String>> patentesNoDisponibles() {
        return ResponseEntity.ok(camionService.obtenerPatentesNoDisponibles());
    }

    // 🔹 GET /camiones/disponible?capacidadPeso=12000&capacidadVolumen=40
    // Devuelve un camión disponible que cumpla con las capacidades mínimas
    @GetMapping("/disponible")
    public ResponseEntity<CamionFiltradoDTO> obtenerCamionPorCapacidad(
            @RequestParam(name = "capacidadPeso") double capacidadPeso,
            @RequestParam(name = "capacidadVolumen") double capacidadVolumen
    ) {
        CamionFiltradoDTO dto = camionService.obtenerCamionDisponiblePorCapacidades(capacidadPeso, capacidadVolumen);
        return ResponseEntity.ok(dto);
    }

    // 🔹 PUT /camiones/{patente}/asignar -> cambia estado a 'N' (no disponible)
    @PutMapping("/{patente}/asignar")
    public ResponseEntity<Void> asignarNoDisponible(@PathVariable String patente) {
        camionService.asignarNoDisponible(patente);
        return ResponseEntity.noContent().build();
    }
}
