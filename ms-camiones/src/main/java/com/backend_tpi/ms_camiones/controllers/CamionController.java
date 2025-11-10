package com.backend_tpi.ms_camiones.controllers;

import com.backend_tpi.ms_camiones.dtos.responses.CamionDisponibleDTO;
import com.backend_tpi.ms_camiones.services.CamionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/camiones")
public class CamionController {

    @Autowired
    private CamionService camionService;

    // GET /api/camiones/disponible
    @GetMapping("/disponible")
    public ResponseEntity<CamionDisponibleDTO> obtenerCamionDisponible() {
        CamionDisponibleDTO dto = camionService.obtenerCamionDisponible();
        return ResponseEntity.ok(dto);
    }
}

