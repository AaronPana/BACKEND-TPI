package com.backend_tpi.ms_rutas.controllers;

import com.backend_tpi.ms_rutas.models.Tramo;
import com.backend_tpi.ms_rutas.services.TramoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tramos")
public class TramoController {
    private TramoService tramoService;

    TramoController(TramoService tramoService) {
        this.tramoService = tramoService;
    }

    @GetMapping
    public ResponseEntity<List<Tramo>> getTramo(){
       List<Tramo> ListaTramos = tramoService.findAll();
        return ResponseEntity.ok(ListaTramos);
    }

    @GetMapping("/{id}")
    public Tramo getTramoById(@PathVariable("id") Long id){
        return tramoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Tramo no encontrado"));
    }
}
