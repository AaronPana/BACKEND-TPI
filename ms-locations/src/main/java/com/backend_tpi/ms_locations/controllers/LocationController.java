package com.backend_tpi.ms_locations.controllers;

import com.backend_tpi.ms_locations.dtos.requests.RutaDtoReq;
import com.backend_tpi.ms_locations.dtos.responses.RutaAlternativaDtoRes;
import com.backend_tpi.ms_locations.dtos.responses.RutaDtoRes;
import com.backend_tpi.ms_locations.external.dtos.responses.RutaOsrmDtoRes;
import com.backend_tpi.ms_locations.services.CacheRutaService;
import com.backend_tpi.ms_locations.services.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

  public final LocationService locationService;
  public final CacheRutaService cacheRutaService;

  @GetMapping("/distancia")
  public ResponseEntity<RutaOsrmDtoRes> getDistancia(
      @RequestParam double latOrigen,
      @RequestParam double lonOrigen,
      @RequestParam double latDestino,
      @RequestParam double lonDestino
  ) {
    RutaOsrmDtoRes rutaOsrmDtoRes = this.locationService.getRutaDirecta(
        latOrigen, lonOrigen, latDestino, lonDestino
    );

    return  ResponseEntity.ok(rutaOsrmDtoRes);
  }

  @PostMapping("/rutas")
  public ResponseEntity<RutaDtoRes> getRutas(
      @RequestBody RutaDtoReq rutaDtoReq) {

    RutaDtoRes rutaDtoRes = this.locationService.getRutasAlternativas(rutaDtoReq);

    return ResponseEntity.ok(rutaDtoRes);
  }

  @GetMapping("/rutas/{idConsulta}/{idRuta}")
  public ResponseEntity<RutaAlternativaDtoRes> getRutaEspecifica(
      @PathVariable String idConsulta,
      @PathVariable String idRuta) {

    return this.cacheRutaService.getSpecificRoute(idConsulta, idRuta)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/rutas/{idConsulta}")
  public ResponseEntity<RutaDtoRes> getConsulta(@PathVariable String idConsulta) {

    return this.cacheRutaService.getFullQuery(idConsulta)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
