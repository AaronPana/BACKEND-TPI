package com.backend_tpi.ms_camiones.services;

import com.backend_tpi.ms_camiones.dtos.responses.CamionDisponibleDTO;
import com.backend_tpi.ms_camiones.exceptions.BaseException;
import com.backend_tpi.ms_camiones.models.Camion;
import com.backend_tpi.ms_camiones.repositories.CamionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CamionService {

    @Autowired
    private CamionRepository camionRepository;

    public CamionDisponibleDTO obtenerCamionDisponible() {
        Camion cam = camionRepository
                .findFirstByEstaDisponibleOrderByPatenteAsc("S")
                .orElseThrow(() -> BaseException.notFoundById("Camion disponible", "S"));

        return new CamionDisponibleDTO(
                cam.getPatente(),
                cam.getCapacidadPeso(),
                cam.getCapacidadVolumen(),
                cam.getConsumoPromedio().doubleValue(),
                cam.getTarifa().getCostoXKilometro().doubleValue()
        );
    }
}
