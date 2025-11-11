package com.backend_tpi.ms_contenedores.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend_tpi.ms_contenedores.models.Depositos;
import com.backend_tpi.ms_contenedores.repositories.DepositoRepository;

@Service
public class DepositoService {

    private DepositoRepository depositoRepository;

    public DepositoService(DepositoRepository depositoRepository) {
        this.depositoRepository = depositoRepository;
    }

    public List<Depositos> findAll() {
        return depositoRepository.findAll();
    }

    public Optional<Depositos> findById(Long id) {
        return depositoRepository.findById(id);
    }
}
