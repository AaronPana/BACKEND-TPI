package com.backend_tpi.ms_camiones.controllers;

import com.backend_tpi.ms_camiones.dtos.responses.CamionDisponibleDTO;
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

    // GET /camiones/disponibles/patentes -> List<String>
    @GetMapping("/disponibles/patentes")
    public ResponseEntity<List<String>> patentesDisponibles() {
        return ResponseEntity.ok(camionService.obtenerPatentesDisponibles());
    }

    // GET /camiones/no-disponibles/patentes -> List<String>
    @GetMapping("/no-disponibles/patentes")
    public ResponseEntity<List<String>> patentesNoDisponibles() {
        return ResponseEntity.ok(camionService.obtenerPatentesNoDisponibles());
    }

    // PUT /camiones/{patente}/asignar -> 204 No Content
    @PutMapping("/{patente}/asignar")
    public ResponseEntity<Void> asignarNoDisponible(@PathVariable String patente) {
        camionService.asignarNoDisponible(patente);
        return ResponseEntity.noContent().build();
    }

    // GET /camiones/disponible -> CamionDisponibleDTO
    @GetMapping("/disponible")
    public ResponseEntity<CamionDisponibleDTO> camionDisponible() {
        return ResponseEntity.ok(camionService.obtenerUnCamionDisponible());
    }
}
