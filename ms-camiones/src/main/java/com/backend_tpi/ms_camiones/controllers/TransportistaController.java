package com.backend_tpi.ms_camiones.controllers;

import com.backend_tpi.ms_camiones.dtos.responses.TransportistaResponse;
import com.backend_tpi.ms_camiones.services.TransportistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/transportistas")
public class TransportistaController {

    @Autowired
    private TransportistaService transportistaService;

    // 🔹 GET /transportistas/{legajo} -> { "legajo": 100 }
    @GetMapping("/{legajo}")
    public ResponseEntity<Map<String, Integer>> obtenerSoloLegajo(@PathVariable Integer legajo) {
        Integer legajoEncontrado = transportistaService.obtenerSoloLegajo(legajo);
        return ResponseEntity.ok(Map.of("legajo", legajoEncontrado));
    }

    // 🔹 GET /transportistas/datos/{legajo} -> datos completos
    @GetMapping("/datos/{legajo}")
    public ResponseEntity<TransportistaResponse> obtenerDatosPorLegajo(@PathVariable Integer legajo) {
        TransportistaResponse dto = transportistaService.obtenerDatosPorLegajo(legajo);
        return ResponseEntity.ok(dto);
    }
}


