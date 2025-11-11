package com.backend_tpi.ms_camiones.services;

import com.backend_tpi.ms_camiones.dtos.responses.CamionDisponibleDTO;
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

    public List<String> obtenerPatentesDisponibles() {
        return camionRepository.findByEstaDisponible("S")
                .stream()
                .map(Camion::getPatente)
                .toList();
    }

    public List<String> obtenerPatentesNoDisponibles() {
        return camionRepository.findByEstaDisponible("N")
                .stream()
                .map(Camion::getPatente)
                .toList();
    }

    /** Cambia el estado del camión a NO disponible ('N'). */
    public void asignarNoDisponible(String patente) {
        Camion camion = camionRepository.findById(patente)
                .orElseThrow(() -> BaseException.notFoundById("Camion", patente));
        if (!"N".equalsIgnoreCase(camion.getEstaDisponible())) {
            camion.setEstaDisponible("N");
            camionRepository.save(camion);
        }
    }

    /** Devuelve la info del primer camión disponible ('S'). */
    public CamionDisponibleDTO obtenerUnCamionDisponible() {
        Camion cam = camionRepository
                .findFirstByEstaDisponibleOrderByPatenteAsc("S")
                .orElseThrow(() -> BaseException.notFoundById("Camion disponible", "S"));

        return new CamionDisponibleDTO(
                cam.getPatente(),
                cam.getCapacidadPeso(),
                cam.getCapacidadVolumen(),
                cam.getConsumoPromedio(),
                cam.getTarifa().getCostoXKilometro()
        );
    }
}
