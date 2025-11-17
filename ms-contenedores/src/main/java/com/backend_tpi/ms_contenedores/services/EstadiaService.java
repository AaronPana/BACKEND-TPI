package com.backend_tpi.ms_contenedores.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.backend_tpi.ms_contenedores.dtos.CostoEstadiaEstimadaDTO;
import com.backend_tpi.ms_contenedores.dtos.CostoEstadiaRealDTO;
import com.backend_tpi.ms_contenedores.dtos.EstadiaRequestDTO;
import com.backend_tpi.ms_contenedores.models.Depositos;
import com.backend_tpi.ms_contenedores.models.Estadias;
import com.backend_tpi.ms_contenedores.repositories.DepositoRepository;
import com.backend_tpi.ms_contenedores.repositories.EstadiaRepository;

@Service
public class EstadiaService {

    private final EstadiaRepository estadiaRepository;
    private final DepositoRepository depositoRepository;

    public EstadiaService(EstadiaRepository estadiaRepository,
                          DepositoRepository depositoRepository) {
        this.estadiaRepository = estadiaRepository;
        this.depositoRepository = depositoRepository;
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

    public Estadias crearEstadia(EstadiaRequestDTO request) {
                // Validaciones básicas del request
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request inválido");
        }
        if (request.getFechaHoraInicio() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fechaHoraInicio es obligatoria");
        }
        if (request.getIdContenedor() == null || request.getIdDeposito() == null || request.getIdTraslado() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "idContenedor, idDeposito e idTraslado son obligatorios");
        }

        //obtener deposito
        Depositos deposito = depositoRepository.findById(request.getIdDeposito())
                .orElseThrow(() -> new RuntimeException("Depósito no encontrado"));
        
        double costoXDia = deposito.getCostoXdia();
        
        // Calcular días estimados
        LocalDateTime inicio = request.getFechaHoraInicio();
        LocalDateTime fin = request.getFechaHoraFin();

        double diasEstimados;
        if (fin != null) {
            long horas = Duration.between(inicio, fin).toHours();
            double dias = horas / 24.0;
            // evitar 0 o valores negativos (si fin <= inicio)
            diasEstimados = (dias > 0.0) ? dias : 1.0;
        } else {
            // si no viene fecha fin asumimos 1 día estimado
            diasEstimados = 1.0;
        }

        double costoEstimado = diasEstimados * costoXDia;

        Estadias nuevaEstadia = new Estadias();
        nuevaEstadia.setFechaHoraInicioEstimada(request.getFechaHoraInicio());
        nuevaEstadia.setFechaHoraFinEstimada(request.getFechaHoraFin());
        nuevaEstadia.setFechaHoraInicioReal(null);
        nuevaEstadia.setFechaHoraFinReal(null);
        nuevaEstadia.setCostoEstimado(costoEstimado);
        nuevaEstadia.setCostoReal(0.0);
        nuevaEstadia.setIdContenedor(request.getIdContenedor());
        nuevaEstadia.setIdTraslado(request.getIdTraslado());
        nuevaEstadia.setIdDeposito(request.getIdDeposito());


        return estadiaRepository.save(nuevaEstadia);
    }
}
