package com.backend_tpi.ms_camiones.controllers;

import com.backend_tpi.ms_camiones.models.Ciudad;
import com.backend_tpi.ms_camiones.services.CiudadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ciudades")
public class CiudadController {

    @Autowired
    private CiudadService ciudadService;

    @GetMapping("/{idCiudad}")
    public ResponseEntity<Map<String, String>> obtenerCiudadProvincia(@PathVariable Integer idCiudad) {
        Ciudad ciudad = ciudadService.obtenerCiudadPorId(idCiudad);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("ciudadProvincia", ciudad.getNombre() + " - " + ciudad.getProvincia().getNombre());

        return ResponseEntity.ok(respuesta);
    }
}
