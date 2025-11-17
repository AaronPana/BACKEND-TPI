package com.backend_tpi.ms_rutas.external.clients;

import com.backend_tpi.ms_rutas.exceptions.BaseException;
import com.backend_tpi.ms_rutas.external.dtos.responses.CamionDTO;
import com.backend_tpi.ms_rutas.external.dtos.responses.TransportitaDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CamionesApiClient {

    private final RestClient camionesRestClient;

    public CamionesApiClient(@Qualifier("camionesRestClient") RestClient camionesRestClient) {
        this.camionesRestClient = camionesRestClient;
    }

    public CamionDTO getCamionDisponible(Double Peso, Double Volumen) {
        return camionesRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/camiones/filtrar")
                        .queryParam("capacidadPeso", Peso)
                        .queryParam("capacidadVolumen", Volumen)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    int status = response.getStatusCode().value();
                    throw BaseException.badRequest("Error al obtener la patente");
                })
                .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
                    throw BaseException.internalError("Error al obtener la patente");
                })
                .body(CamionDTO.class);
    }

    public void asignarNoDisponible(String patente) {
        camionesRestClient.put()
                .uri("/camiones/{patente}/asignar", patente)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> { throw BaseException.badRequest("Error al marcar camión como no disponible"); })
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> { throw BaseException.internalError("Error interno al modificar estado del camión"); })
                .toBodilessEntity(); // PUT sin body
    }

    public TransportitaDTO obtenerLegajoTransportista() {
        return camionesRestClient.get()
                .uri("/transportistas/legajo")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> { throw BaseException.badRequest("Error al obtener un legajo de transportista"); })
                .onStatus(HttpStatusCode::is5xxServerError,
                        (req, res) -> { throw BaseException.internalError("Error interno al obtener un legajo de transportista"); })
                .body(TransportitaDTO.class);
    }

    public CamionDTO obtenerCamionPorPatente(String patente) {
        return camionesRestClient.get()
                .uri("/camiones/{patente}", patente)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    throw BaseException.notFound("Camion no encontrado con patente " + patente);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    throw BaseException.internalError("Error al consultar camion con patente " + patente);
                })
                .body(CamionDTO.class);
    }

}
