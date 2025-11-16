package com.backend_tpi.ms_contenedores.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend_tpi.ms_contenedores.dtos.CoordenadasDTO;
import com.backend_tpi.ms_contenedores.dtos.EstadoDTO;
import com.backend_tpi.ms_contenedores.dtos.PesoVolumenDTO;
import com.backend_tpi.ms_contenedores.models.Contenedores;
import com.backend_tpi.ms_contenedores.services.ContenedorService;

@RestController
@RequestMapping("/contenedores")
public class ContenedorController {

    private ContenedorService contenedorService;

    ContenedorController(ContenedorService contenedorService) {
        this.contenedorService = contenedorService;
    }

    @GetMapping
    public ResponseEntity<List<Contenedores>> getContenedores() {
        List<Contenedores> lista = contenedorService.findAll();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public Contenedores getContenedorById(@PathVariable("id") Long id) {
        return contenedorService.findById(id)
                .orElseThrow(() -> new RuntimeException("Contenedor no encontrado"));
    }

    @GetMapping("/{id}/peso-volumen")
    public ResponseEntity<PesoVolumenDTO> getPesoVolumen(@PathVariable("id") Long id) {
        PesoVolumenDTO dto = contenedorService.getPesoVolumen(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Contenedores> crearContenedor(@RequestBody PesoVolumenDTO request) {
        Contenedores creado = contenedorService.crearContenedor(request);
        URI location = URI.create("/contenedores/" + creado.getIdContenedor());
        return ResponseEntity.created(location).body(creado);
    }

    @GetMapping("/{id}/estado")
    public ResponseEntity<EstadoDTO> getEstado(@PathVariable Long id) {
        EstadoDTO dto = contenedorService.getEstadoContenedor(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}/coordenadas")
    public ResponseEntity<Void> actualizarCoordenadas(@PathVariable("id") Long id,
                                                  @RequestBody CoordenadasDTO coordenadas) {
        contenedorService.actualizarCoordenadas(id, coordenadas);
        return ResponseEntity.noContent().build(); // 204 No Content
}
}