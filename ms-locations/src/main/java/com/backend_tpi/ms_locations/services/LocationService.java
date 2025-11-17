package com.backend_tpi.ms_locations.services;

import com.backend_tpi.ms_locations.dtos.responses.DepositoDtoRes;
import com.backend_tpi.ms_locations.dtos.responses.RutaAlternativaDtoRes;
import com.backend_tpi.ms_locations.external.dtos.responses.RutaOsrmDtoRes;
import com.backend_tpi.ms_locations.dtos.requests.RutaDtoReq;
import com.backend_tpi.ms_locations.dtos.responses.RutaDtoRes;
import com.backend_tpi.ms_locations.external.clients.OsrmApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {

  private final OsrmApiClient osrmApiClient;
  private final CacheRutaService cacheRutaService;

  // Simulación de depósitos en Argentina (deberías obtenerlos de tu BD)
  private final List<DepositoDtoRes> depositosHabiles = Arrays.asList(
      new DepositoDtoRes(1L, "Depósito Rosario", -32.9442, -60.6505, 0D),
      new DepositoDtoRes(2L, "Depósito Villa María", -32.4072, -63.2409, 0D),
      new DepositoDtoRes(3L, "Depósito Río Cuarto", -33.1301, -64.3499, 0D),
      new DepositoDtoRes(4L, "Depósito San Francisco", -31.4281, -62.0827, 0D),
      new DepositoDtoRes(5L, "Depósito Bell Ville", -32.6255, -62.6889, 0D)
  );

  public RutaOsrmDtoRes getRutaDirecta(double latitudOrigen, double longitudOrigen,
                                       double latitudDestino, double longitudDestino
  ) {
    return this.osrmApiClient.calcularRuta(longitudOrigen, latitudOrigen, longitudDestino, latitudDestino);
  }

  public RutaDtoRes getRutasAlternativas(RutaDtoReq rutaDtoReq) {
    RutaOsrmDtoRes rutaDirecta = this.osrmApiClient.calcularRuta(
        rutaDtoReq.getLongitudOrigen(), rutaDtoReq.getLatitudOrigen(), rutaDtoReq.getLongitudDestino(), rutaDtoReq.getLatitudDestino()
    );

    RutaAlternativaDtoRes alternativaDirecta = new RutaAlternativaDtoRes(
        "direct",
        Arrays.asList("A", "B"),
        rutaDirecta.getRoutes().getFirst().getDistance(),
        rutaDirecta.getRoutes().getFirst().getDuration(),
        Collections.emptyList()
    );

    double latitudMedia = (rutaDtoReq.getLatitudOrigen() + rutaDtoReq.getLatitudDestino()) / 2;
    double longitudMedia = (rutaDtoReq.getLongitudOrigen() + rutaDtoReq.getLongitudDestino()) / 2;

    List<DepositoDtoRes> depositosCercanos = this.buscarDepositosCercanos(
        rutaDtoReq.getLongitudOrigen(), rutaDtoReq.getLatitudOrigen(),
        rutaDtoReq.getLongitudDestino(), rutaDtoReq.getLatitudDestino(),
        longitudMedia, latitudMedia,
        rutaDtoReq.getMaximosDepositos()
    );

    List<RutaAlternativaDtoRes> rutasAlternativas = this.generarRutasAlternativas(
        rutaDtoReq.getLongitudOrigen(), rutaDtoReq.getLatitudOrigen(),
        rutaDtoReq.getLongitudDestino(), rutaDtoReq.getLatitudDestino(),
        depositosCercanos
    );

    String nextIdConsulta = this.cacheRutaService.getNextIdConsulta();
    RutaDtoRes rutaDtoRes = new RutaDtoRes(nextIdConsulta, alternativaDirecta, rutasAlternativas);
    this.cacheRutaService.storeRouteQuery(nextIdConsulta, rutaDtoRes);
    return rutaDtoRes;
  }

  private List<DepositoDtoRes> buscarDepositosCercanos(double longitudOrigen, double latitudOrigen,
                                                       double longitudDestino, double latitudDestino,
                                                       double longitudMedia, double latitudMedia,
                                                       int maximosDepositos
  ) {
    return depositosHabiles.stream()
        .map(deposito -> {
          double distancia = this.calcularDistanciaHaversine(
              latitudMedia, longitudMedia,
              deposito.getLatitud(), deposito.getLongitud()
          );

          double distanciaDesdeOrigen = this.calcularDistanciaHaversine(
              latitudOrigen, longitudOrigen,
              deposito.getLatitud(), deposito.getLongitud()
          );

          double distanciaTotalRuta = this.calcularDistanciaHaversine(
              latitudOrigen, longitudOrigen,
              latitudDestino, longitudDestino
          );

          DepositoDtoRes posibleDeposito = new DepositoDtoRes(
              deposito.getIdDeposito(),
              deposito.getNombre(),
              deposito.getLatitud(),
              deposito.getLongitud(),
              distancia
          );

          if (distanciaDesdeOrigen <= distanciaTotalRuta / 2) {
            return null;
          }
          return posibleDeposito;
        })
        .filter(Objects::nonNull)
        .sorted(Comparator.comparingDouble(DepositoDtoRes::getDistanciaDesdePuntoMedioRuta))
        .limit(maximosDepositos)
        .collect(Collectors.toList());
  }

  private double calcularDistanciaHaversine(double latitud_1, double longitud_1,
                                            double latitud_2, double longitud_2
  ) {
    final int R = 6371000; // Radio de la Tierra en metros

    double distanciaLatitud = Math.toRadians(latitud_2 - latitud_1);
    double distanciaLongitud = Math.toRadians(longitud_2 - longitud_1);

    double a = Math.sin(distanciaLatitud / 2) * Math.sin(distanciaLatitud / 2) +
        Math.cos(Math.toRadians(latitud_1)) * Math.cos(Math.toRadians(latitud_2)) *
            Math.sin(distanciaLongitud / 2) * Math.sin(distanciaLongitud / 2);

    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return R * c;
  }

  private List<RutaAlternativaDtoRes> generarRutasAlternativas(double longitudOrigen, double latitudOrigen,
                                                               double longitudDestino, double latitudDestino,
                                                               List<DepositoDtoRes> depositos) {
    List<RutaAlternativaDtoRes> rutasAlternativas = new ArrayList<>();

    if (depositos.isEmpty()) {
      return rutasAlternativas;
    }

    // Generar combinaciones de rutas
    List<List<DepositoDtoRes>> combinaciones = new ArrayList<>();

    // Rutas con 1 depósito: A -> D1 -> B, A -> D2 -> B
    for (int i = 0; i < Math.min(2, depositos.size()); i++) {
      combinaciones.add(Collections.singletonList(depositos.get(i)));
    }

    // Rutas con 2 depósitos: A -> D1 -> D2 -> B, A -> D1 -> D3 -> B
    if (depositos.size() >= 2) {
      combinaciones.add(Arrays.asList(depositos.get(0), depositos.get(1)));
    }
    if (depositos.size() >= 3) {
      combinaciones.add(Arrays.asList(depositos.get(0), depositos.get(2)));
    }

    long idAlternativa = 1;
    // Calcular todas las alternativas
    for (List<DepositoDtoRes> combo : combinaciones) {
      rutasAlternativas.add(this.calcularRutaConDepositos(
          idAlternativa, longitudOrigen, latitudOrigen, longitudDestino, latitudDestino, combo
      ));
      idAlternativa++;
    }

    return rutasAlternativas;
  }

  private RutaAlternativaDtoRes calcularRutaConDepositos(long idAlternativa, double longitudOrigen, double latitudOrigen,
                                                         double longitudDestino, double latitudDestino,
                                                         List<DepositoDtoRes> depositos
  ) {
    // Construir waypoints: origen -> depósitos -> destino
    double[][] waypoints = new double[depositos.size() + 2][2];
    waypoints[0] = new double[]{longitudOrigen, latitudOrigen};

    for (int i = 0; i < depositos.size(); i++) {
      waypoints[i + 1] = new double[]{depositos.get(i).getLongitud(), depositos.get(i).getLatitud()};
    }

    waypoints[waypoints.length - 1] = new double[]{longitudDestino, latitudDestino};

    // Consultar OSRM
    RutaOsrmDtoRes rutaOsrmDto = this.osrmApiClient.calcularMultiplesPuntos(waypoints);

    // Construir path como ["A", "D1", "D2", "B"]
    List<String> path = new ArrayList<>();
    path.add("A");
    depositos.forEach(deposito -> path.add(deposito.getNombre()));
    path.add("B");

    return new RutaAlternativaDtoRes(
        "alt-" + idAlternativa,
        path,
        rutaOsrmDto.getRoutes().getFirst().getDistance(),
        rutaOsrmDto.getRoutes().getFirst().getDuration(),
        depositos
    );
  }
}
