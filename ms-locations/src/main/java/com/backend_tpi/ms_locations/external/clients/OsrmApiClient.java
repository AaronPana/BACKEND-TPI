package com.backend_tpi.ms_locations.external.clients;

import com.backend_tpi.ms_locations.exceptions.BaseException;
import com.backend_tpi.ms_locations.external.dtos.responses.RutaOsrmDtoRes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Locale;


@Component
public class OsrmApiClient {

  private final RestClient osrmRestClient;

  public OsrmApiClient(@Qualifier("osrmRestClient") RestClient osrmRestClient) {
    this.osrmRestClient = osrmRestClient;
  }

  public RutaOsrmDtoRes calcularRuta(double longitudOrigen, double latitudOrigen, double longitudDestino, double latitudDestino) {
    String coordenadas = String.format(Locale.US, "%.6f,%.6f;%.6f,%.6f", longitudOrigen, latitudOrigen, longitudDestino, latitudDestino);

    String url = String.format("/route/v1/driving/%s", coordenadas);

    String fullUrl = UriComponentsBuilder.fromPath(url)
        .queryParam("overview", "false")
        .build()
        .toUriString();

    return osrmRestClient.get()
        .uri(fullUrl)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
          throw BaseException.badRequest("Error al obtener la ruta");
        })
        .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
          throw BaseException.internalError("Error al obtener la ruta");
        })
        .body(RutaOsrmDtoRes.class);
  }

  // waypoints = [
  //    [lon1, lat1],
  //    [lon2, lat2],
  //    [lon3, lat3]
  // ]
  public RutaOsrmDtoRes calcularMultiplesPuntos(double[][] waypoints) {
    StringBuilder coordenadas = new StringBuilder();

    for (int i = 0; i < waypoints.length; i++) {
      if (i > 0) coordenadas.append(";");

      coordenadas.append(
          String.format(Locale.US, "%.6f,%.6f", waypoints[i][0], waypoints[i][1])
      );
    }

    String url = String.format("/route/v1/driving/%s", coordenadas);

    String fullUrl = UriComponentsBuilder.fromPath(url)
        .queryParam("overview", "false")
        .build()
        .toUriString();

    return osrmRestClient.get()
        .uri(fullUrl)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
          throw BaseException.badRequest("Error al obtener la ruta");
        })
        .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
          throw BaseException.internalError("Error al obtener la ruta");
        })
        .body(RutaOsrmDtoRes.class);
  }
}
