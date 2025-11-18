package com.backend_tpi.ms_rutas.services;

import com.backend_tpi.ms_rutas.constants.EstadoTramo;
import com.backend_tpi.ms_rutas.constants.TipoTramo;
import com.backend_tpi.ms_rutas.dtos.requests.TramoRequestDTO;
import com.backend_tpi.ms_rutas.dtos.responses.HojaDeRutaDTO;
import com.backend_tpi.ms_rutas.dtos.responses.TramoDTO;
import com.backend_tpi.ms_rutas.exceptions.BaseException;
import com.backend_tpi.ms_rutas.external.clients.CamionesApiClient;
import com.backend_tpi.ms_rutas.external.clients.ContenedoresApiClient;

import com.backend_tpi.ms_rutas.external.clients.LocationsApiClient;
import com.backend_tpi.ms_rutas.external.dtos.responses.*;
import com.backend_tpi.ms_rutas.mappers.TramoMapper;
import com.backend_tpi.ms_rutas.models.Tramo;
import com.backend_tpi.ms_rutas.repositories.TramoRepository;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TramoService {

    private final TramoRepository tramoRepository;
    private final CamionesApiClient camionesApiClient;
    private final ContenedoresApiClient contenedoresApiClient;
    private final LocationsApiClient locationsApiClient;


    @Value("${costoCombustible}")
    private Double costoCombustible;

    public TramoService(TramoRepository tramoRepository,
                        CamionesApiClient camionesApiClient,
                        ContenedoresApiClient contenedoresApiClient,
                        LocationsApiClient locationsApiClient
    ) {
        this.tramoRepository = tramoRepository;
        this.camionesApiClient = camionesApiClient;
        this.contenedoresApiClient = contenedoresApiClient;
        this.locationsApiClient = locationsApiClient;

    }


    public List<Tramo> findAll() {
        return tramoRepository.findAll();
    }

    public Tramo findById(Long idTramo) {
        return tramoRepository.findById(idTramo)
                .orElseThrow(() -> BaseException.notFoundById("Tramo", idTramo));
    }

    public CoordenadaDTO buscarCoordenada(Long idTramo) {
        return camionesApiClient.obtenerCoordenadasCiudades(idTramo);
    }

    public RutaDtoRes generarAlternativasDesdeTraslado(TramoRequestDTO dto) {
        // obtener todas las coordenadas
        CoordenadaDTO ori = camionesApiClient.obtenerCoordenadasCiudades(dto.getIdCiudadOrigen());
        CoordenadaDTO des = camionesApiClient.obtenerCoordenadasCiudades(dto.getIdCiudadDestino());

        //Construir request para ms-location y llamar
        Map<String, Object> reqBody = new HashMap<>();
        reqBody.put("latitudOrigen", ori.getLatitud());
        reqBody.put("longitudOrigen", ori.getLongitud());
        reqBody.put("latitudDestino", des.getLatitud());
        reqBody.put("longitudDestino", des.getLongitud());
        reqBody.put("maximosDepositos", dto.getMaximosDepositos());

        RutaDtoRes rutaDtoRes = locationsApiClient.generarAlternativas(reqBody);
        return rutaDtoRes;
    }

    @Transactional
    public List<TramoDTO> create(TramoRequestDTO dto) {

        if (dto.getIdConsulta() == null || dto.getIdRuta() == null) {
            throw BaseException.badRequest("idConsulta y idRuta son requeridos para persistir la ruta");
        }

        //Pedir ruta seleccionada a location
        RutaAlternativaDtoRes ruta = locationsApiClient.getSpecificRoute(dto.getIdConsulta(), dto.getIdRuta());
        if (ruta == null) {
            throw BaseException.badRequest("Ruta no encontrada");
        }

        //Obtener coords de origen y destino para calcular tramos
        CoordenadaDTO ori = camionesApiClient.obtenerCoordenadasCiudades(dto.getIdCiudadOrigen());
        CoordenadaDTO des = camionesApiClient.obtenerCoordenadasCiudades(dto.getIdCiudadDestino());

        // construir lista ordendada de puntos
        List<PointMeta> puntos = new ArrayList<>();
        puntos.add(new PointMeta(ori.getLatitud(), ori.getLongitud(), null, dto.getDireccionOrigen()));

        // depositos en el orden de la ruta (ruta.getDeposit os)
        if (ruta.getDepositos() != null) {
            for (DepositoDtoRes dep : ruta.getDepositos()) {
                puntos.add(new PointMeta(dep.getLatitud(), dep.getLongitud(), dep.getIdDeposito(), dep.getNombre()));
            }
        }

        puntos.add(new PointMeta(des.getLatitud(), des.getLongitud(), null, dto.getDireccionDestino()));

        //Recorrer segmentos consecutivos y crear tramos
        List<TramoDTO> res = new ArrayList<>();
        LocalDateTime inicioEncadenado = LocalDateTime.now();

        for (int i = 0; i < puntos.size() - 1; i++) {
            PointMeta a = puntos.get(i);
            PointMeta b = puntos.get(i + 1);

            // 4.1 obtener distancia/duración entre a y b desde ms-location
            RutaOsrmDtoRes r = locationsApiClient.getDistanceBetween(a.lat, a.lon, b.lat, b.lon);
            double distanciaMeters = r.getRoutes().get(0).getDistance();
            double duracionSeconds = r.getRoutes().get(0).getDuration();

            // 4.2 crear Tramo
            Tramo t = new Tramo();
            t.setDireccionOrigen(a.label); // DEBE venir de ms-traslados para origen/destino; para depósitos usamos nombre
            t.setDireccionDestino(b.label);

            t.setIdDepositoOrigen(a.idDeposito);
            t.setIdDepositoDestino(b.idDeposito);

            t.setIdCiudadOrigen(dto.getIdCiudadOrigen());
            t.setIdCiudadDestino(dto.getIdCiudadDestino()); // podés refinar si querés ciudad por tramo

            t.setIdTraslado(dto.getIdTraslado());
            t.setEstadoTramo(EstadoTramo.ASIGNADO);
            t.setIdTraslado(dto.getIdTraslado());

            // determinar tipoTramo
            if (a.idDeposito == null && b.idDeposito == null) t.setTipoTramo(TipoTramo.ORIGEN_DESTINO);
            else if (a.idDeposito == null && b.idDeposito != null) t.setTipoTramo(TipoTramo.ORIGEN_DEPOSITO);
            else if (a.idDeposito != null && b.idDeposito == null) t.setTipoTramo(TipoTramo.DEPOSITO_DESTINO);
            else t.setTipoTramo(TipoTramo.DEPOSITO_DEPOSITO);

            // asignar camion/transportista
            CamionDTO camion = camionesApiClient.getCamionDisponible(dto.getPesoContenedor(), dto.getVolumenContenedor());
            t.setPatenteCamion(camion.getPatente());
            camionesApiClient.asignarNoDisponible(camion.getPatente());

            TransportitaDTO transportista = camionesApiClient.obtenerLegajoTransportista();
            t.setLegajoTransportista(transportista.getLegajo());

            // fechas estimadas encadenadas
            t.setFechaHoraInicioEstimada(inicioEncadenado);
            t.setFechaHoraFinEstimada(inicioEncadenado.plusSeconds((long) duracionSeconds));
            inicioEncadenado = t.getFechaHoraFinEstimada();

            // costo estimado: convertimos a km
            double distanciaKm = distanciaMeters / 1000.0;
            Double costoEstimado = calcularCostoEstimado(camion.getConsumoPromedio(), distanciaKm, camion.getCostoXKilometro());
            t.setCostoEstimado(costoEstimado);

            // crear estadía en depósito origen si corresponde
            if (t.getIdDepositoOrigen() != null && dto.getIdContenedor() != null) {
                try {
                    contenedoresApiClient.crearEstadia(dto.getIdContenedor(), dto.getIdTraslado(), t.getIdDepositoOrigen(), LocalDateTime.now());
                } catch (Exception e) {
                    // si falló la creación de estadía podés decidir si abortar o solo loggear
                }
            }

            Tramo saved = tramoRepository.save(t);
            res.add(TramoMapper.toDTO(saved));
        }
        return res;
    }


    // helper
    private record PointMeta(Double lat, Double lon, Long idDeposito, String label) {
    }

    public Double calcularCostoEstimado(Double consumoPromedio, Double distanciaTotal, Double costoXKm) {
        return (consumoPromedio * costoCombustible) + (distanciaTotal * costoXKm);
    }


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

    public Double calcularCostoReal(Double consumoPromedio, Double distanciaReal, Double costoXKm) {
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








