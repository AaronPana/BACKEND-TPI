package com.backend_tpi.ms_camiones.services;

import com.backend_tpi.ms_camiones.exceptions.BaseException;
import com.backend_tpi.ms_camiones.models.Ciudad;
import com.backend_tpi.ms_camiones.repositories.CiudadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CiudadService {

    @Autowired
    private CiudadRepository ciudadRepository;

    public List<Ciudad> listarTodas() {
        return ciudadRepository.findAll();
    }

    public Ciudad obtenerCiudadPorId(Long idCiudad) {
        return ciudadRepository.findById(idCiudad)
                .orElseThrow(() -> BaseException.notFoundById("Ciudad", idCiudad));
    }


    public List<Ciudad> listarPorProvincia(Long idProvincia) {
        return ciudadRepository.findByProvincia_IdProvincia(idProvincia);
    }
}
