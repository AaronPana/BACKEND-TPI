package com.backend_tpi.ms_camiones.controllers;

import com.backend_tpi.ms_camiones.dtos.responses.TransportistaResponse;
import com.backend_tpi.ms_camiones.models.Transportista;
import com.backend_tpi.ms_camiones.services.TransportistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transportistas")
public class TransportistaController {

    @Autowired
    private TransportistaService transportistaService;

    @GetMapping
    public ResponseEntity<List<Transportista>> listarTodos() {
        return ResponseEntity.ok(transportistaService.listarTodos());
    }

    @GetMapping("/{legajo}")
    public ResponseEntity<Transportista> obtenerPorLegajo(@PathVariable Integer legajo) {
        Transportista transportista = transportistaService.obtenerPorLegajo(legajo);
        return ResponseEntity.ok(transportista);
    }

    @PostMapping
    public ResponseEntity<Transportista> crear(@RequestBody Transportista transportista) {
        Transportista creado = transportistaService.crear(transportista);
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{legajo}")
    public ResponseEntity<Transportista> actualizar(@PathVariable Integer legajo,
                                                    @RequestBody Transportista transportista) {
        Transportista actualizado = transportistaService.actualizar(legajo, transportista);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{legajo}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer legajo) {
        transportistaService.eliminar(legajo);
        return ResponseEntity.noContent().build();
    }

    // NUEVO: devuelve un legajo aleatorio
    @GetMapping("/legajo")
    public ResponseEntity<Map<String, Integer>> obtenerLegajoAleatorio() {
        Integer legajo = transportistaService.obtenerLegajoAleatorio();
        return ResponseEntity.ok(Map.of("legajo", legajo));
    }
}


