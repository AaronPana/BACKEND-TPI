package com.backend_tpi.ms_rutas.controllers;

import com.backend_tpi.ms_rutas.constants.EstadoTramo;
import com.backend_tpi.ms_rutas.dtos.requests.TramoRequestDTO;
import com.backend_tpi.ms_rutas.dtos.responses.HojaDeRutaDTO;
import com.backend_tpi.ms_rutas.dtos.responses.TramoDTO;
import com.backend_tpi.ms_rutas.external.clients.CamionesApiClient;
import com.backend_tpi.ms_rutas.external.dtos.responses.CamionDTO;
import com.backend_tpi.ms_rutas.models.Tramo;
import com.backend_tpi.ms_rutas.services.TramoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/tramos")
public class TramoController {
    private TramoService tramoService;
    private CamionesApiClient camionesApiClient;

    TramoController(TramoService tramoService, CamionesApiClient camionesApiClient) {

        this.tramoService = tramoService;
        this.camionesApiClient = camionesApiClient;
    }

    @GetMapping
    public ResponseEntity<List<Tramo>> getTramo() {
        List<Tramo> ListaTramos = tramoService.findAll();
        return ResponseEntity.ok(ListaTramos);
    }

    @GetMapping("/{id}")
    public Tramo getTramoById(@PathVariable("id") Long idTramo) {
        return tramoService.findById(idTramo);

    }

    @PostMapping
    public ResponseEntity<Tramo> create(@RequestBody TramoRequestDTO tramo) {
        Tramo creado = tramoService.create(tramo);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long idTramo) {
        tramoService.delete(idTramo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/estado")
    public ResponseEntity<Map<String, EstadoTramo>> getEstadoTramo(@PathVariable("id") Long idTramo) {
        EstadoTramo estadoTramo = tramoService.obtenerEstadoTramo(idTramo);

        Map<String, EstadoTramo> respuesta = new HashMap<>();
        respuesta.put("EstadoTramo", estadoTramo);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/transportista/{legajo}")
    public ResponseEntity<List<TramoDTO>> obtenerTramosPorTransportista(@PathVariable Long legajo) {
        List<TramoDTO> tramos = tramoService.obtenerTramosPorTransportista(legajo);
        return ResponseEntity.ok(tramos);
    }

    @PutMapping("/{id}/inicio-real")
    public ResponseEntity<TramoDTO> asignarFechaInicioReal(
            @PathVariable("id") Long idTramo,
            @RequestParam LocalDateTime fechaInicioReal) {

        TramoDTO tramoActualizado = tramoService.asignarFechaInicioReal(idTramo, fechaInicioReal);
        return ResponseEntity.ok(tramoActualizado);
    }

    @PutMapping("/{id}/fin-real")
    public ResponseEntity<TramoDTO> asignarFechaFinReal(
            @PathVariable("id") Long idTramo,
            @RequestParam("fechaFinReal") LocalDateTime fechaFinReal) {

        TramoDTO dto = tramoService.asignarFechaFinReal(idTramo, fechaFinReal);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/hoja-de-ruta/{idTraslado}")
    public HojaDeRutaDTO getHojaDeRuta(@PathVariable Long idTraslado) {

        return tramoService.getHojaDeRuta(idTraslado);
    }

    @GetMapping("/disponible")
    public CamionDTO testCamionDisponible(
            @RequestParam Double peso,
            @RequestParam Double volumen) {

        return camionesApiClient.getCamionDisponible(peso, volumen);
    }




}
