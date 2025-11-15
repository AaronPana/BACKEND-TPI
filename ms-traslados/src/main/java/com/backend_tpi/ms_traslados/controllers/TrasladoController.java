package com.backend_tpi.ms_traslados.controllers;

import com.backend_tpi.ms_traslados.dtos.requests.TrasladoPatchDtoReq;
import com.backend_tpi.ms_traslados.dtos.requests.TrasladoPostDtoReq;
import com.backend_tpi.ms_traslados.dtos.responses.TrasladoDetalleDtoRes;
import com.backend_tpi.ms_traslados.dtos.responses.TrasladoMetricasDtoRes;
import com.backend_tpi.ms_traslados.dtos.responses.TrasladoResumenDtoRes;
import com.backend_tpi.ms_traslados.services.TrasladoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
  public ResponseEntity<TrasladoResumenDtoRes> create(@RequestBody @Valid TrasladoPostDtoReq trasladoPostDtoReq) {
    TrasladoResumenDtoRes trasladoSinClienteDtoRes = this.trasladoService.create(trasladoPostDtoReq);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{idTraslado}")
        .buildAndExpand(trasladoSinClienteDtoRes.getIdTraslado())
        .toUri();

    return ResponseEntity.created(location).body(trasladoSinClienteDtoRes);
  }

  @GetMapping("/{idTraslado}")
  public ResponseEntity<TrasladoDetalleDtoRes> getById(@PathVariable Long idTraslado) {
    TrasladoDetalleDtoRes trasladoDetalleDtoRes = this.trasladoService.getById(idTraslado);
    return ResponseEntity.ok(trasladoDetalleDtoRes);
  }

  @GetMapping("/{idTraslado}/metricas")
  public ResponseEntity<TrasladoMetricasDtoRes> getMetricasById(@PathVariable Long idTraslado) {
    TrasladoMetricasDtoRes trasladoMetricasDtoRes = this.trasladoService.getMetricasById(idTraslado);
    return ResponseEntity.ok(trasladoMetricasDtoRes);
  }

  @PatchMapping("/{idTraslado}")
  public ResponseEntity<TrasladoDetalleDtoRes> partialUpdate(
      @PathVariable Long idTraslado,
      @RequestBody @Valid TrasladoPatchDtoReq trasladoPatchDtoReq) {

    TrasladoDetalleDtoRes trasladoDetalleDtoRes = this.trasladoService.actualizarParcial(idTraslado, trasladoPatchDtoReq);
    return ResponseEntity.ok(trasladoDetalleDtoRes);
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
