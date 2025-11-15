package com.backend_tpi.ms_contenedores.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.backend_tpi.ms_contenedores.dtos.CoordenadasDTO;
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

     public CoordenadasDTO getCoordenadas(Long idDeposito) {
        Depositos deposito = depositoRepository.findById(idDeposito)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Deposito no encontrado"));

        // Supongo getters getLatitud() / getLongitud() en la entidad
        return new CoordenadasDTO(deposito.getLatitud(), deposito.getLongitud());
    }


}
