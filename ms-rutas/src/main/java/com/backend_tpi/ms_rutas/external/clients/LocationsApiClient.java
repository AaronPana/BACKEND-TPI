package com.backend_tpi.ms_rutas.external.clients;


import com.backend_tpi.ms_rutas.exceptions.BaseException;
import com.backend_tpi.ms_rutas.external.dtos.responses.RutaAlternativaDtoRes;
import com.backend_tpi.ms_rutas.external.dtos.responses.RutaDtoRes;
import com.backend_tpi.ms_rutas.external.dtos.responses.RutaOsrmDtoRes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


@Component
public class LocationsApiClient {
    private final RestClient locationsRestClient;

    public LocationsApiClient(@Qualifier("locationsRestClient") RestClient locationsRestClient) {
        this.locationsRestClient = locationsRestClient;
    }
    // Generar alternativas: POST /api/locations/rutas
    public RutaDtoRes generarAlternativas(Object requestBody) {
        return locationsRestClient.post()
                .uri("/api/locations/rutas")
                .body(requestBody)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req,res)-> { throw BaseException.badRequest("Error en ms-location"); })
                .onStatus(HttpStatusCode::is5xxServerError, (req,res)-> { throw BaseException.serviceUnavailable("ms-location"); })
                .body(RutaDtoRes.class);
    }

    // Obtener ruta seleccionada: GET /api/locations/rutas/{idConsulta}/{idRuta}
    public RutaAlternativaDtoRes getSpecificRoute(String idConsulta, String idRuta) {
        return locationsRestClient.get()
                .uri(uri -> uri.path("/api/locations/rutas/{idConsulta}/{idRuta}").build(idConsulta, idRuta))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req,res)-> { throw BaseException.badRequest("Ruta no encontrada"); })
                .onStatus(HttpStatusCode::is5xxServerError, (req,res)-> { throw BaseException.serviceUnavailable("ms-location"); })
                .body(RutaAlternativaDtoRes.class);
    }

    // Consultar distancia entre dos coordenadas: GET /api/locations/distancia
    public RutaOsrmDtoRes getDistanceBetween(double latOrigen, double lonOrigen, double latDestino, double lonDestino) {
        return locationsRestClient.get()
                .uri(uri -> uri.path("/api/locations/distancia")
                        .queryParam("latOrigen", latOrigen)
                        .queryParam("lonOrigen", lonOrigen)
                        .queryParam("latDestino", latDestino)
                        .queryParam("lonDestino", lonDestino)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req,res)-> { throw BaseException.badRequest("Error en distancia"); })
                .onStatus(HttpStatusCode::is5xxServerError, (req,res)-> { throw BaseException.serviceUnavailable("ms-location"); })
                .body(RutaOsrmDtoRes.class);
    }
}

