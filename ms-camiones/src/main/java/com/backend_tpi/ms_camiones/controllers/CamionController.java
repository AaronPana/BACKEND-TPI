package com.backend_tpi.ms_camiones.controllers;

import com.backend_tpi.ms_camiones.services.CamionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/camiones")
public class CamionController {

    @Autowired
    private CamionService camionService;

    // GET /api/camiones/disponible  →  { "patente": "AB123CD" }
    @GetMapping("/disponible")
    public ResponseEntity<Map<String, String>> obtenerPatenteDisponible() {
        String patente = camionService.obtenerPatenteCamionDisponible();
        if (patente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of("patente", patente));
    }
}

