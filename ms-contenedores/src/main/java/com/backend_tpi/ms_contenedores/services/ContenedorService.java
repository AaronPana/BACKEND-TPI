package com.backend_tpi.ms_contenedores.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.backend_tpi.ms_contenedores.constants.EstadoContenedor;
import com.backend_tpi.ms_contenedores.dtos.CoordenadasDTO;
import com.backend_tpi.ms_contenedores.dtos.EstadoDTO;
import com.backend_tpi.ms_contenedores.dtos.PesoVolumenDTO;
import com.backend_tpi.ms_contenedores.models.Contenedores;
import com.backend_tpi.ms_contenedores.repositories.ContenedorRepository;

import jakarta.transaction.Transactional;

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

    public Contenedores crearContenedor(PesoVolumenDTO request) {
        // Validaciones claras y tempranas
        if (request == null || request.getPeso() == null || request.getVolumen() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Peso y volumen son obligatorios");
        }

        Contenedores nuevo = new Contenedores();
        nuevo.setPeso(request.getPeso());
        nuevo.setVolumen(request.getVolumen());

        // calcula costo de forma segura (no devuelve null)
        Double costo = calcularCosto(request.getPeso(), request.getVolumen());
        if (costo == null) {
            costo = 0.0;
        }

        nuevo.setCostoXpesoXvolumen(costo);
        nuevo.setEstadoContenedores(EstadoContenedor.ASIGNADO);
        nuevo.setLatitud(0.0);
        nuevo.setLongitud(0.0);

        return contenedorRepository.save(nuevo);
    }

    private Double calcularCosto(Double peso, Double volumen) {
        if (peso == null || volumen == null) {
            return 0.0;
        }
        double tarifaPesoVolumen = 0.5; // ajustá según reglas de negocio
        return peso * volumen * tarifaPesoVolumen;
    }

    public EstadoDTO getEstadoContenedor(Long id) {

    Contenedores contenedor = contenedorRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Contenedor no encontrado"));

    return new EstadoDTO(contenedor.getEstadoContenedores().name());
    }

    @Transactional
    public void actualizarCoordenadas(Long id, CoordenadasDTO coordenadas) {
        if (coordenadas == null || coordenadas.getLatitud() == null || coordenadas.getLongitud() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coordenadas inválidas");
        }
        Contenedores contenedor = contenedorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Contenedor no encontrado"));
        
        contenedor.setLatitud(coordenadas.getLatitud());
        contenedor.setLongitud(coordenadas.getLongitud());

        contenedorRepository.save(contenedor);
    }    
}