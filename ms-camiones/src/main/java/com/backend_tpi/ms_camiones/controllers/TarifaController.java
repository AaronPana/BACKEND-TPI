package com.backend_tpi.ms_camiones.controllers;

import com.backend_tpi.ms_camiones.dtos.TarifaDTO;
import com.backend_tpi.ms_camiones.services.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarifas")
public class TarifaController {

    @Autowired
    private TarifaService tarifaService;

    @GetMapping
    public ResponseEntity<List<TarifaDTO>> listarTodas() {
        return ResponseEntity.ok(tarifaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarifaDTO> obtenerPorId(@PathVariable Integer id) {
        TarifaDTO dto = tarifaService.obtenerPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<TarifaDTO> crear(@RequestBody TarifaDTO dto) {
        TarifaDTO creada = tarifaService.crear(dto);
        return ResponseEntity.ok(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TarifaDTO> actualizar(@PathVariable Integer id,
                                                @RequestBody TarifaDTO dto) {
        TarifaDTO actualizado = tarifaService.actualizar(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        tarifaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
