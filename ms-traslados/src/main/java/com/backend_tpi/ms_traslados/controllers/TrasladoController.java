package com.backend_tpi.ms_traslados.controllers;

import com.backend_tpi.ms_traslados.dtos.requests.TrasladoPostDtoReq;
import com.backend_tpi.ms_traslados.dtos.responses.TrasladoSinClienteDtoRes;
import com.backend_tpi.ms_traslados.services.TrasladoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/traslados")
@RequiredArgsConstructor
public class TrasladoController {

  private final TrasladoService trasladoService;

  @PostMapping
  public ResponseEntity<TrasladoSinClienteDtoRes> create(@RequestBody @Valid TrasladoPostDtoReq trasladoPostDtoReq) {
    TrasladoSinClienteDtoRes trasladoSinClienteDtoRes = this.trasladoService.create(trasladoPostDtoReq);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{idTraslado}")
        .buildAndExpand(trasladoSinClienteDtoRes.getIdTraslado())
        .toUri();

    return ResponseEntity.created(location).body(trasladoSinClienteDtoRes);
  }

  @GetMapping("/{idTraslado}")
  public ResponseEntity<TrasladoSinClienteDtoRes> getById(@PathVariable Long idTraslado) {
    TrasladoSinClienteDtoRes trasladoSinClienteDtoRes = this.trasladoService.getById(idTraslado);
    return ResponseEntity.ok(trasladoSinClienteDtoRes);
  }

  @DeleteMapping("/{idTraslado}")
  public ResponseEntity<Map<String, String>> delete(@PathVariable Long idTraslado) {
    this.trasladoService.delete(idTraslado);
    return ResponseEntity.ok(Map.of(
        "message", "Traslado eliminado con éxito",
        "idTraslado", idTraslado.toString()
    ));
  }
}
