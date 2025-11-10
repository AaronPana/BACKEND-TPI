package com.backend_tpi.ms_camiones.controllers;

import com.backend_tpi.ms_camiones.dtos.TarifaDTO;
import com.backend_tpi.ms_camiones.services.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarifas")
public class TarifaController {

    @Autowired
    private TarifaService tarifaService;

    // 🔹 PUT existente
    @PutMapping("/{id}")
    public ResponseEntity<TarifaDTO> actualizar(@PathVariable Integer id,
                                                @RequestBody TarifaDTO dto) {
        TarifaDTO actualizado = tarifaService.actualizar(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    // 🔹 NUEVO GET
    @GetMapping("/{id}")
    public ResponseEntity<TarifaDTO> obtenerPorId(@PathVariable Integer id) {
        TarifaDTO dto = tarifaService.obtenerPorId(id);
        return ResponseEntity.ok(dto);
    }
}
