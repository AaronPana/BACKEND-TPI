package com.backend_tpi.ms_contenedores.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}