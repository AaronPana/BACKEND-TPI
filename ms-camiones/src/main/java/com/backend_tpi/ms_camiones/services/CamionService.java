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

    /**
     * Devuelve las patentes de todos los camiones disponibles ('S')
     */
    public List<String> obtenerPatentesDisponibles() {
        return camionRepository.findByEstaDisponible("S")
                .stream()
                .map(Camion::getPatente)
                .toList();
    }

    /**
     * Devuelve las patentes de todos los camiones no disponibles ('N')
     */
    public List<String> obtenerPatentesNoDisponibles() {
        return camionRepository.findByEstaDisponible("N")
                .stream()
                .map(Camion::getPatente)
                .toList();
    }

    /**
     * Cambia el estado de un camión a no disponible ('N')
     */
    public void asignarNoDisponible(String patente) {
        Camion camion = camionRepository.findById(patente)
                .orElseThrow(() -> BaseException.notFoundById("Camion", patente));

        if (!"N".equalsIgnoreCase(camion.getEstaDisponible())) {
            camion.setEstaDisponible("N");
            camionRepository.save(camion);
        }
    }

    /**
     * Devuelve UN camión disponible ('S') que cumpla:
     * capacidadPeso >= capacidadPesoReq
     * capacidadVolumen >= capacidadVolumenReq
     */
    public CamionFiltradoDTO obtenerCamionDisponiblePorCapacidades(double capacidadPesoReq, double capacidadVolumenReq) {
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
                cam.getConsumoPromedio()
        );
    }
}
