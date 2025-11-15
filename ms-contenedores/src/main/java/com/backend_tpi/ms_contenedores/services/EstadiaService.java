package com.backend_tpi.ms_contenedores.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend_tpi.ms_contenedores.dtos.CostoEstadiaEstimadaDTO;
import com.backend_tpi.ms_contenedores.dtos.CostoEstadiaRealDTO;
import com.backend_tpi.ms_contenedores.models.Estadias;
import com.backend_tpi.ms_contenedores.repositories.EstadiaRepository;

@Service
public class EstadiaService {

    private EstadiaRepository estadiaRepository;

    public EstadiaService(EstadiaRepository estadiaRepository) {
        this.estadiaRepository = estadiaRepository;
    }

    public List<Estadias> findAll() {
        return estadiaRepository.findAll();
    }

    public Optional<Estadias> findById(Long id) {
        return estadiaRepository.findById(id);
    }

    public CostoEstadiaEstimadaDTO obtenerCostoEstadiaEstimada(Long id) {
        Optional<Estadias> estadia = estadiaRepository.findById(id);
        if (estadia.isPresent()) {
            return new CostoEstadiaEstimadaDTO(estadia.get().getCostoEstimado());
        }
        return null; // o lanzar excepción si preferís
    }

    public CostoEstadiaRealDTO obtenerCostoEstadiaReal(Long id) {
        Optional<Estadias> estadia = estadiaRepository.findById(id);
        if (estadia.isPresent()) {
            return new CostoEstadiaRealDTO(estadia.get().getCostoReal());
        }
        return null;
    }
}
