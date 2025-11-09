package com.backend_tpi.ms_camiones.services;

import com.backend_tpi.ms_camiones.models.Camion;
import com.backend_tpi.ms_camiones.repositories.CamionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CamionService {

    @Autowired
    private CamionRepository camionRepository;

    /**
     * Devuelve la patente de un camión disponible ('S') o null si no hay.
     */
    public String obtenerPatenteCamionDisponible() {

        return camionRepository
                .findFirstByEstaDisponibleOrderByPatenteAsc("S")
                .map(Camion::getPatente)
                .orElse(null);
    }
}
