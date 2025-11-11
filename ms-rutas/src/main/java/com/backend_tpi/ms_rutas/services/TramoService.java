package com.backend_tpi.ms_rutas.services;

import com.backend_tpi.ms_rutas.constants.EstadoTramo;
import com.backend_tpi.ms_rutas.constants.TipoTramo;
import com.backend_tpi.ms_rutas.dtos.responses.HojaDeRutaDTO;
import com.backend_tpi.ms_rutas.dtos.responses.RutaSeleccionadaDTO;
import com.backend_tpi.ms_rutas.dtos.responses.TramoDTO;
import com.backend_tpi.ms_rutas.exceptions.BaseException;
import com.backend_tpi.ms_rutas.mappers.TramoMapper;
import com.backend_tpi.ms_rutas.models.Tramo;
import com.backend_tpi.ms_rutas.repositories.TramoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Tramo findById(Long idTramo) {
        return tramoRepository.findById(idTramo)
                .orElseThrow(() -> BaseException.notFoundById("Tramo", idTramo));
    }

    public Tramo create(Tramo tramo) {
        if (tramo.getIdTramo() != null) {
            throw BaseException.badRequest("No se debe especificar un ID al crear un tramo nuevo");
        }
        // Como vamos a manejar el tema de los estados al crear un nuevo tramo?  Se usa ASIGNADO
        // Al crear un tramo los datos de creacion son:
        // FechaHoraInicioEstimada
        // FechaHoraFinEstimada
        // costoEstimado (Esto deberia ser de acuerdo al tiempo?)
        // direccionOrigen
        // direccionDestino
        // estado con el predeterminado
        // tipoTramo?
        //

        return tramoRepository.save(tramo);
    }

    public void delete(Long idTramo) {
        Tramo tramo = tramoRepository.findById(idTramo)
                .orElseThrow(() -> BaseException.notFoundById("Tramo", idTramo));
        tramoRepository.delete(tramo);
    }

    public void asignarCamionDisponible() {

    }


    public EstadoTramo obtenerEstadoTramo(Long idTramo) {
        Tramo tramo = findById(idTramo);
        return tramo.getEstadoTramo();
    }

    public List<TramoDTO> obtenerTramosPorTransportista(Long legajoTransportista) {
        List<Tramo> tramos = tramoRepository.findByLegajoTransportista(legajoTransportista);
        if (tramos.isEmpty()) {
            throw BaseException.notFound("No se encontraron tramos asignados al transportista con legajo " + legajoTransportista);
        }
        return tramos.stream()
                .map(TramoMapper::toDTO)
                .toList();
    }

    public TramoDTO asignarFechaInicioReal(Long idTramo, LocalDateTime fechaInicioReal) {
        Tramo tramo = findById(idTramo);

        if (tramo.getFechaHoraInicioReal() != null) {
            throw BaseException.badRequest("El tramo ya tiene una fecha de inicio real asignada.");
        }

        // Asigna la fecha real de inicio y cambia el estado
        tramo.setFechaHoraInicioReal(fechaInicioReal);
        tramo.setEstadoTramo(EstadoTramo.INICIADO);

        Tramo tramoActualizado = tramoRepository.save(tramo);

        // Retorna el DTO
        return TramoMapper.toDTO(tramoActualizado);
    }

    public TramoDTO asignarFechaFinReal(Long idTramo, LocalDateTime fechaFinReal) {
        Tramo tramo = findById(idTramo);

        if (tramo.getFechaHoraFinReal() != null) {
            throw BaseException.badRequest("El tramo ya tiene una fecha de fin real asignada.");
        }
        tramo.setFechaHoraFinReal(fechaFinReal);
        tramo.setEstadoTramo(EstadoTramo.FINALIZADO);

        Tramo tramoActualizado = tramoRepository.save(tramo);
        return TramoMapper.toDTO(tramoActualizado);

    }

    public Tramo guardarRutaSeleccionada(RutaSeleccionadaDTO dto) {
        Tramo tramo = new Tramo();

        tramo.setDireccionOrigen(dto.getDireccionOrigen());
        tramo.setDireccionDestino(dto.getDireccionDestino());
        tramo.setCostoEstimado(dto.getCostoEstimado());
        tramo.setFechaHoraInicioEstimada(LocalDateTime.now());
        tramo.setEstadoTramo(EstadoTramo.ASIGNADO);
        tramo.setTipoTramo(TipoTramo.valueOf(dto.getTipoTramo())); // Ej: ORIGEN_DESTINO

        tramo.setPatenteCamion(dto.getPatenteCamion());
        tramo.setLegajoTransportista(dto.getLegajoTransportista());
        tramo.setIdDepositoOrigen(dto.getIdDepositoOrigen());
        tramo.setIdDepositoDestino(dto.getIdDepositoDestino());
        tramo.setIdTraslado(dto.getIdTraslado());
        tramo.setIdCiudadOrigen(dto.getIdCiudadOrigen());
        tramo.setIdCiudadDestino(dto.getIdCiudadDestino());

        return tramoRepository.save(tramo);
    }

    public HojaDeRutaDTO getHojaDeRuta(Long idTraslado) {
        List<Tramo> tramos = tramoRepository.findByIdTrasladoOrderByIdTramoAsc(idTraslado);

        if (tramos.isEmpty()) {
            throw BaseException.notFound("No se encontraron tramos para el traslado con ID " + idTraslado);
        }

        return new HojaDeRutaDTO(
                idTraslado,
                tramos.size(),
                tramos
        );
    }
}








