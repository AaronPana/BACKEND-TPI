package com.backend_tpi.ms_contenedores.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend_tpi.ms_contenedores.dtos.responses.PesoVolumenDTO;
import com.backend_tpi.ms_contenedores.models.Contenedores;
import com.backend_tpi.ms_contenedores.repositories.ContenedorRepository;

@Service
public class ContenedorService {

    private final ContenedorRepository contenedorRepository;

    public ContenedorService(ContenedorRepository contenedorRepository) {
        this.contenedorRepository = contenedorRepository;
    }

    public List<Contenedores> findAll() {
        return contenedorRepository.findAll();
    }

    public Optional<Contenedores> findById(Long id) {
        return contenedorRepository.findById(id);
    }

    
    public PesoVolumenDTO getPesoVolumen(Long id) {
        Contenedores c = contenedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contenedor no encontrado"));
        return new PesoVolumenDTO(c.getPeso(), c.getVolumen());
    }

}