package com.backend_tpi.ms_rutas.services;

import com.backend_tpi.ms_rutas.models.Tramo;
import com.backend_tpi.ms_rutas.repositories.TramoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TramoService {

    private TramoRepository tramoRepository;

    public TramoService(TramoRepository tramoRepository) {
        this.tramoRepository = tramoRepository;
    }

    public List<Tramo> findAll() {
        return tramoRepository.findAll();
    }

    public Optional<Tramo> findById(Long id) {
        return tramoRepository.findById(id);
    }
}
