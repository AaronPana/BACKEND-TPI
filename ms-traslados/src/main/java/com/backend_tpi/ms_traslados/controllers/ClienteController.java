package com.backend_tpi.ms_traslados.controllers;

import com.backend_tpi.ms_traslados.dtos.responses.ClienteDtoRes;
import com.backend_tpi.ms_traslados.services.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

  private final ClienteService clienteService;

  @GetMapping
  public ResponseEntity<List<ClienteDtoRes>> getAll() {
    List<ClienteDtoRes> clientes = this.clienteService.getAll();
    return ResponseEntity.ok(clientes);
  }
}
