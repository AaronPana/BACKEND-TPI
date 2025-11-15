package com.backend_tpi.ms_camiones.controllers;

import com.backend_tpi.ms_camiones.models.Provincia;
import com.backend_tpi.ms_camiones.services.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/provincias")
public class ProvinciaController {

    @Autowired
    private ProvinciaService provinciaService;

    @GetMapping
    public ResponseEntity<List<Provincia>> listarTodas() {
        return ResponseEntity.ok(provinciaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Provincia> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(provinciaService.obtenerPorId(id));
    }

}
