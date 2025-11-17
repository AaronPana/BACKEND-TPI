package com.backend_tpi.ms_rutas.services;

import com.backend_tpi.ms_rutas.constants.EstadoTramo;
import com.backend_tpi.ms_rutas.constants.TipoTramo;
import com.backend_tpi.ms_rutas.dtos.requests.TramoRequestDTO;
import com.backend_tpi.ms_rutas.dtos.responses.HojaDeRutaDTO;
import com.backend_tpi.ms_rutas.dtos.responses.RutaSeleccionadaDTO;
import com.backend_tpi.ms_rutas.dtos.responses.TramoDTO;
import com.backend_tpi.ms_rutas.exceptions.BaseException;
import com.backend_tpi.ms_rutas.external.clients.CamionesApiClient;
import com.backend_tpi.ms_rutas.external.clients.ContenedoresApiClient;
import com.backend_tpi.ms_rutas.external.dtos.responses.CamionDTO;
import com.backend_tpi.ms_rutas.external.dtos.responses.CostoEstadiaDTO;
import com.backend_tpi.ms_rutas.external.dtos.responses.TransportitaDTO;
import com.backend_tpi.ms_rutas.mappers.TramoMapper;
import com.backend_tpi.ms_rutas.models.Tramo;
import com.backend_tpi.ms_rutas.repositories.TramoRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TramoService {

    private final TramoRepository tramoRepository;
    private final CamionesApiClient camionesApiClient;
    private final ContenedoresApiClient contenedoresApiClient;

    @Value("${costoCombustible}")
    private Double costoCombustible;

    public TramoService(TramoRepository tramoRepository, CamionesApiClient camionesApiClient, ContenedoresApiClient contenedoresApiClient) {
        this.tramoRepository = tramoRepository;
        this.camionesApiClient = camionesApiClient;
        this.contenedoresApiClient = contenedoresApiClient;
    }


    public List<Tramo> findAll() {
        return tramoRepository.findAll();
    }

    public Tramo findById(Long idTramo) {
        return tramoRepository.findById(idTramo)
                .orElseThrow(() -> BaseException.notFoundById("Tramo", idTramo));
    }


    public Tramo create(TramoRequestDTO request) {

        Tramo tramo = new Tramo();
        tramo.setDireccionOrigen(request.getDireccionOrigen());
        tramo.setDireccionDestino(request.getDireccionDestino());
        tramo.setIdCiudadOrigen(request.getIdCiudadOrigen());
        tramo.setIdCiudadDestino(request.getIdCiudadDestino());
        tramo.setIdTraslado(request.getIdTraslado());
        tramo.setEstadoTramo(EstadoTramo.ASIGNADO);
        tramo.setIdTraslado(request.getIdTraslado());
        tramo.setTipoTramo(TipoTramo.ORIGEN_DESTINO);

        //Buscar Camion y lo asigna como no disponible
        CamionDTO camion = camionesApiClient.getCamionDisponible(
                request.getPesoContenedor(),
                request.getVolumenContenedor()
        );
        tramo.setPatenteCamion(camion.getPatente());
        camionesApiClient.asignarNoDisponible(camion.getPatente());

        //Busqueda de transportista
        TransportitaDTO transportista = camionesApiClient.obtenerLegajoTransportista();
        tramo.setLegajoTransportista(transportista.getLegajo());


        //crear estadia si poseo un idDepositoOrigen o Destino
        /*if(request.getIdDepositoOrigen() != null){
            contenedoresApiClient.crearEstadia(
                    request.getIdContenedor(),
                    request.getIdTraslado(),
                    request.getIdDepositoOrigen(),
                    LocalDateTime.now() );
        }*/

        //Calculo de Costos estimado
        Double distanciaPorAhora = 24000.0;
        tramo.setCostoEstimado(
                calcularCostoEstimado(camion.getConsumoPromedio(), distanciaPorAhora, camion.getCostoXKilometro())
        );

        return tramoRepository.save(tramo);

    }

    public Double calcularCostoEstimado(Double consumoPromedio, Double distanciaTotal, Double costoXKm) {
        return (consumoPromedio * costoCombustible) + (distanciaTotal * costoXKm);
    }



    // La fecha inicioEstimada seria

    // La fecha finalEstimada se calcula con el tiempo que devuelve osrm

    /*
     public Tramo guardarRutaSeleccionada(RutaSeleccionadaDTO dto) {
        Tramo tramo = new Tramo();

        tramo.setDireccionOrigen(dto.getDireccionOrigen());
        tramo.setDireccionDestino(dto.getDireccionDestino());
        tramo.setCostoEstimado(dto.getCostoEstimado());
        tramo.setFechaHoraInicioEstimada(LocalDateTime.now());
        tramo.setEstadoTramo(EstadoTramo.ASIGNADO);
        tramo.setTipoTramo(TipoTramo.valueOf(dto.getTipoTramo())); // Ej: ORIGEN_DESTINO

        tramo.setPatenteCamion(dto.getPatenteCamion());
        camionesApiClient.asignarNoDisponible(dto.getPatenteCamion());

        tramo.setLegajoTransportista(dto.getLegajoTransportista());
        tramo.setIdDepositoOrigen(dto.getIdDepositoOrigen());
        tramo.setIdDepositoDestino(dto.getIdDepositoDestino());
        tramo.setIdTraslado(dto.getIdTraslado());
        tramo.setIdCiudadOrigen(dto.getIdCiudadOrigen());
        tramo.setIdCiudadDestino(dto.getIdCiudadDestino());

        return tramoRepository.save(tramo);
    }
    * */

    public void delete(Long idTramo) {
        Tramo tramo = tramoRepository.findById(idTramo)
                .orElseThrow(() -> BaseException.notFoundById("Tramo", idTramo));
        tramoRepository.delete(tramo);
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

    public TramoDTO asignarFechaInicioReal(Long idTramo) {
        Tramo tramo = findById(idTramo);

        if (tramo.getFechaHoraInicioReal() != null) {
            throw BaseException.badRequest("El tramo ya tiene una fecha de inicio real asignada.");
        }

        // Asigna la fecha real de inicio y cambia el estado
        tramo.setFechaHoraInicioReal(LocalDateTime.now());
        tramo.setEstadoTramo(EstadoTramo.INICIADO);

        Tramo tramoActualizado = tramoRepository.save(tramo);

        return TramoMapper.toDTO(tramoActualizado);
    }

    public CamionDTO obtenerCamionPorPatente(String patente) {
        return camionesApiClient.obtenerCamionPorPatente(patente);
    }

    public TramoDTO asignarFechaFinReal(Long idTramo) {
        Tramo tramo = findById(idTramo);

        if (tramo.getFechaHoraFinReal() != null) {
            throw BaseException.badRequest("El tramo ya tiene una fecha de fin real asignada.");
        }

        if (tramo.getFechaHoraInicioReal() == null) {
            throw BaseException.badRequest("No se puede asignar una fecha de fin " +
                    "si no esta asignada la fecha de inico");
        }

        tramo.setFechaHoraFinReal(LocalDateTime.now());
        CamionDTO camion = obtenerCamionPorPatente(tramo.getPatenteCamion());
        Double distanciaPorAhora = 24000.0;

        tramo.setCostoReal(
                calcularCostoReal(camion.getConsumoPromedio(), distanciaPorAhora, camion.getTarifa().getCostoXKilometro())
        );
        tramo.setEstadoTramo(EstadoTramo.FINALIZADO);

        Tramo tramoActualizado = tramoRepository.save(tramo);
        return TramoMapper.toDTO(tramoActualizado);
    }

    public Double calcularCostoReal(Double consumoPromedio, Double distanciaReal, Double costoXKm){
        return (consumoPromedio * costoCombustible) + (distanciaReal * costoXKm);
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








