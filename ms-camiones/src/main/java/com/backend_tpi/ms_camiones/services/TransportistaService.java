package com.backend_tpi.ms_camiones.services;

import com.backend_tpi.ms_camiones.dtos.responses.TransportistaResponse;
import com.backend_tpi.ms_camiones.exceptions.BaseException;
import com.backend_tpi.ms_camiones.models.Transportista;
import com.backend_tpi.ms_camiones.repositories.TransportistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransportistaService {

    @Autowired
    private TransportistaRepository transportistaRepository;

    /** Devuelve solo el legajo */
    public Integer obtenerSoloLegajo(Integer legajo) {
        Transportista transportista = transportistaRepository.findById(legajo)
                .orElseThrow(() -> BaseException.notFoundById("Transportista", legajo));
        return transportista.getLegajo();
    }

    /** Devuelve los datos completos del transportista */
    public TransportistaResponse obtenerDatosPorLegajo(Integer legajo) {
        Transportista t = transportistaRepository.findById(legajo)
                .orElseThrow(() -> BaseException.notFoundById("Transportista", legajo));

        return new TransportistaResponse(
                t.getLegajo(),
                t.getNombre(),
                t.getApellido(),
                t.getTelefono(),
                t.getEmail(),
                t.getDireccion()
        );
    }
}

