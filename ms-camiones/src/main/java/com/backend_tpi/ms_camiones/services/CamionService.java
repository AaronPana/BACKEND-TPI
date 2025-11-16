package com.backend_tpi.ms_camiones.services;

import com.backend_tpi.ms_camiones.dtos.responses.CamionFiltradoDTO;
import com.backend_tpi.ms_camiones.exceptions.BaseException;
import com.backend_tpi.ms_camiones.models.Camion;
import com.backend_tpi.ms_camiones.repositories.CamionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CamionService {

    @Autowired
    private CamionRepository camionRepository;

    public List<Camion> listarTodos() {
        return camionRepository.findAll();
    }

    public Camion obtenerPorPatente(String patente) {
        return camionRepository.findById(patente)
                .orElseThrow(() -> BaseException.notFoundById("Camion", patente));
    }

    public Camion crear(Camion camion) {
        if (camion.getEstaDisponible() == null || camion.getEstaDisponible().isBlank()) {
            camion.setEstaDisponible("S");
        }
        return camionRepository.save(camion);
    }

    public Camion actualizar(String patente, Camion datos) {
        Camion existente = obtenerPorPatente(patente);
        existente.setCapacidadPeso(datos.getCapacidadPeso());
        existente.setCapacidadVolumen(datos.getCapacidadVolumen());
        existente.setConsumoPromedio(datos.getConsumoPromedio());
        existente.setEstaDisponible(datos.getEstaDisponible());
        existente.setTarifa(datos.getTarifa());
        return camionRepository.save(existente);
    }

    public void eliminar(String patente) {
        Camion existente = obtenerPorPatente(patente);
        camionRepository.delete(existente);
    }

    public List<Camion> listarDisponibles() {
        return camionRepository.findByEstaDisponible("S");
    }

        public CamionFiltradoDTO obtenerCamionDisponiblePorCapacidades(double capacidadPesoReq,
                                                                       double capacidadVolumenReq) {

            Camion cam = camionRepository
                    .findFirstByEstaDisponibleAndCapacidadPesoGreaterThanEqualAndCapacidadVolumenGreaterThanEqualOrderByPatenteAsc(
                            "S",
                            capacidadPesoReq,
                            capacidadVolumenReq
                    )
                    .orElseThrow(() -> BaseException.notFoundById(
                            "Camion disponible con las capacidades mínimas",
                            String.format("peso >= %.2f volumen >= %.2f", capacidadPesoReq, capacidadVolumenReq)
                    ));

            return new CamionFiltradoDTO(
                    cam.getPatente(),
                    cam.getCapacidadPeso(),
                    cam.getCapacidadVolumen(),
                    cam.getConsumoPromedio(),
                    cam.getTarifa().getCostoXKilometro()   // 👈 acá traemos la tarifa
            );

    }

    // Cambia la disponibilidad: si está 'S' pasa a 'N' y si no, pasa a 'S'
    public void toggleDisponibilidad(String patente) {
        Camion existente = obtenerPorPatente(patente);

        String estadoActual = existente.getEstaDisponible();
        if ("S".equalsIgnoreCase(estadoActual)) {
            existente.setEstaDisponible("N");
        } else {
            existente.setEstaDisponible("S");
        }

        camionRepository.save(existente);
    }
}
