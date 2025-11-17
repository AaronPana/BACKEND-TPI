package com.backend_tpi.ms_contenedores.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend_tpi.ms_contenedores.dtos.CostoEstadiaEstimadaDTO;
import com.backend_tpi.ms_contenedores.dtos.CostoEstadiaRealDTO;
import com.backend_tpi.ms_contenedores.dtos.EstadiaRequestDTO;
import com.backend_tpi.ms_contenedores.models.Estadias;
import com.backend_tpi.ms_contenedores.services.EstadiaService;

@RestController
@RequestMapping("/estadias")
public class EstadiaController {

    private EstadiaService estadiaService;

    EstadiaController(EstadiaService estadiaService) {
        this.estadiaService = estadiaService;
    }

    @GetMapping
    public ResponseEntity<List<Estadias>> getEstadias() {
        List<Estadias> lista = estadiaService.findAll();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public Estadias getEstadiaById(@PathVariable("id") Long id) {
        return estadiaService.findById(id)
                .orElseThrow(() -> new RuntimeException("Estadía no encontrada"));
    }

    @GetMapping("/{id}/costo-estadia-estimada")
    public ResponseEntity<CostoEstadiaEstimadaDTO> obtenerCostoEstadiaEstimada(@PathVariable Long id) {
        CostoEstadiaEstimadaDTO dto = estadiaService.obtenerCostoEstadiaEstimada(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}/costo-estadia-real")
    public ResponseEntity<CostoEstadiaRealDTO> obtenerCostoEstadiaReal(@PathVariable Long id) {
        CostoEstadiaRealDTO dto = estadiaService.obtenerCostoEstadiaReal(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Estadias> crearEstadia(@RequestBody EstadiaRequestDTO request) {
        Estadias creada = estadiaService.crearEstadia(request);
        URI location = URI.create("/estadias/" + creada.getIdEstadia());
        return ResponseEntity.created(location).body(creada);
    }
}
