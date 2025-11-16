package com.backend_tpi.ms_camiones.controllers;

import com.backend_tpi.ms_camiones.dtos.responses.CamionFiltradoDTO;
import com.backend_tpi.ms_camiones.models.Camion;
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

    @GetMapping
    public ResponseEntity<List<Camion>> listarTodos() {
        return ResponseEntity.ok(camionService.listarTodos());
    }

    @GetMapping("/{patente}")
    public ResponseEntity<Camion> obtenerPorPatente(@PathVariable String patente) {
        Camion camion = camionService.obtenerPorPatente(patente);
        return ResponseEntity.ok(camion);
    }

    @PostMapping
    public ResponseEntity<Camion> crear(@RequestBody Camion camion) {
        Camion creado = camionService.crear(camion);
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{patente}")
    public ResponseEntity<Camion> actualizar(@PathVariable String patente,
                                             @RequestBody Camion camion) {
        Camion actualizado = camionService.actualizar(patente, camion);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{patente}")
    public ResponseEntity<Void> eliminar(@PathVariable String patente) {
        camionService.eliminar(patente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Camion>> listarDisponibles() {
        return ResponseEntity.ok(camionService.listarDisponibles());
    }

    @GetMapping("/filtrar")
    public ResponseEntity<CamionFiltradoDTO> obtenerCamionPorCapacidad(
            @RequestParam(name = "capacidadPeso") Double capacidadPeso,
            @RequestParam(name = "capacidadVolumen") Double capacidadVolumen
    ) {
        CamionFiltradoDTO dto = camionService.obtenerCamionDisponiblePorCapacidades(
                capacidadPeso,
                capacidadVolumen
        );
        return ResponseEntity.ok(dto);
    }
    @PutMapping("/{patente}/estado")
    public ResponseEntity<Void> asignarNoDisponible(@PathVariable String patente) {
        camionService.toggleDisponibilidad(patente);
        return ResponseEntity.noContent().build();
    }
}
