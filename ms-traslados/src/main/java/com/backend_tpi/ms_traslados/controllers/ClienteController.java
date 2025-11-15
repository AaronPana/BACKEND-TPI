package com.backend_tpi.ms_traslados.controllers;

import com.backend_tpi.ms_traslados.dtos.requests.ClientePostDtoReq;
import com.backend_tpi.ms_traslados.dtos.responses.ClienteDtoRes;
import com.backend_tpi.ms_traslados.dtos.responses.FullClienteDtoRes;
import com.backend_tpi.ms_traslados.services.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

  private final ClienteService clienteService;

  @PostMapping
  public ResponseEntity<ClienteDtoRes> create(@RequestBody @Valid ClientePostDtoReq clientePostDtoReq) {
    ClienteDtoRes clienteDtoRes = this.clienteService.create(clientePostDtoReq);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{idCliente}/traslados")
        .buildAndExpand(clienteDtoRes.getNroDocumento())
        .toUri();

    return ResponseEntity.created(location).body(clienteDtoRes);
  }

  @GetMapping
  public ResponseEntity<List<ClienteDtoRes>> getAll() {
    List<ClienteDtoRes> clientes = this.clienteService.getAll();
    return ResponseEntity.ok(clientes);
  }

  @GetMapping("/{idCliente}/traslados")
  public ResponseEntity<FullClienteDtoRes> getById(@PathVariable Long idCliente) {
    FullClienteDtoRes fullClienteDtoRes = this.clienteService.getById(idCliente);
    return ResponseEntity.ok(fullClienteDtoRes);
  }

  @DeleteMapping("/{idCliente}")
  public ResponseEntity<Map<String, String>> delete(@PathVariable Long idCliente) {
    this.clienteService.delete(idCliente);
    return ResponseEntity.ok(Map.of(
        "message", "Cliente eliminado con éxito",
        "idCliente", idCliente.toString()
    ));
  }

}
