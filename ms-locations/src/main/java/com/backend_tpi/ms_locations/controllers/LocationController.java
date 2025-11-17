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

  /**
   * Endpoint 1: Calcular distancia directa entre dos puntos
   * GET /api/locations/distancia?latOrigen=-31.4135&lonOrigen=-64.18105&latDestino=-32.9471&lonDestino=-60.6985
   */
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

  /**
   * Endpoint 2: Calcular todas las alternativas de ruta con depósitos
   * POST /api/locations/rutas
   * Body: {
   *   "originLat": -31.4135,
   *   "originLon": -64.18105,
   *   "destLat": -32.9471,
   *   "destLon": -60.6985,
   *   "maxDeposits": 3
   * }
   */
  @PostMapping("/rutas")
  public ResponseEntity<RutaDtoRes> getRutas(
      @RequestBody RutaDtoReq rutaDtoReq) {

    RutaDtoRes rutaDtoRes = this.locationService.getRutasAlternativas(rutaDtoReq);

    return ResponseEntity.ok(rutaDtoRes);
  }

  /**
   * Endpoint 3: Obtener una ruta específica previamente calculada
   * GET /api/locations/routes/{idConsulta}/{idRuta}
   *
   * Ejemplo: GET /api/locations/rutas/query-1234567890-456/alt-2
   */
  @GetMapping("/rutas/{idConsulta}/{idRuta}")
  public ResponseEntity<RutaAlternativaDtoRes> getRutaEspecifica(
      @PathVariable String idConsulta,
      @PathVariable String idRuta) {

    return this.cacheRutaService.getSpecificRoute(idConsulta, idRuta)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Endpoint 4: Obtener toda la consulta original
   * GET /api/locations/rutas/{idConsulta}
   */
  @GetMapping("/rutas/{idConsulta}")
  public ResponseEntity<RutaDtoRes> getConsulta(@PathVariable String idConsulta) {

    return this.cacheRutaService.getFullQuery(idConsulta)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
