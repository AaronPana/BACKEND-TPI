package com.backend_tpi.ms_camiones.services;

import com.backend_tpi.ms_camiones.exceptions.BaseException;
import com.backend_tpi.ms_camiones.models.Provincia;
import com.backend_tpi.ms_camiones.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinciaService {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    public List<Provincia> listarTodas() {
        return provinciaRepository.findAll();
    }

    public Provincia obtenerPorId(Long id) {
        return provinciaRepository.findById(id)
                .orElseThrow(() -> BaseException.notFoundById("Provincia", id));
    }

}
