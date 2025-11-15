package com.backend_tpi.ms_camiones.controllers;

import com.backend_tpi.ms_camiones.models.Ciudad;
import com.backend_tpi.ms_camiones.services.CiudadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ciudades")
public class CiudadController {

    @Autowired
    private CiudadService ciudadService;

    @GetMapping
    public ResponseEntity<List<Ciudad>> listarTodas() {
        return ResponseEntity.ok(ciudadService.listarTodas());
    }

    @GetMapping("/{idCiudad}")
    public ResponseEntity<Ciudad> obtenerPorId(@PathVariable Long idCiudad) {
        Ciudad ciudad = ciudadService.obtenerCiudadPorId(idCiudad);
        return ResponseEntity.ok(ciudad);
    }


    @GetMapping("/por-provincia/{idProvincia}")
    public ResponseEntity<List<Ciudad>> listarPorProvincia(@PathVariable Long idProvincia) {
        return ResponseEntity.ok(ciudadService.listarPorProvincia(idProvincia));
    }

    // Ciudad - Provincia como texto (para otros MS)
    @GetMapping("/{idCiudad}/descripcion")
    public ResponseEntity<Map<String, String>> obtenerCiudadProvincia(@PathVariable Long idCiudad) {
        Ciudad ciudad = ciudadService.obtenerCiudadPorId(idCiudad);

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("ciudadProvincia", ciudad.getNombre() + " - " + ciudad.getProvincia().getNombre());

        return ResponseEntity.ok(respuesta);
    }
}

