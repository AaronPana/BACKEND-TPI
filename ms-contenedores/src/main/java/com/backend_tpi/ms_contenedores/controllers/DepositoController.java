package com.backend_tpi.ms_contenedores.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend_tpi.ms_contenedores.models.Depositos;
import com.backend_tpi.ms_contenedores.services.DepositoService;

@RestController
@RequestMapping("/depositos")
public class DepositoController {

    private DepositoService depositoService;

    DepositoController(DepositoService depositoService) {
        this.depositoService = depositoService;
    }

    @GetMapping
    public ResponseEntity<List<Depositos>> getDepositos() {
        List<Depositos> lista = depositoService.findAll();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public Depositos getDepositoById(@PathVariable("id") Long id) {
        return depositoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Depósito no encontrado"));
    }
}
