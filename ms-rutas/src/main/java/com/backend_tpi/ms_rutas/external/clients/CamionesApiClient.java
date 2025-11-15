package com.backend_tpi.ms_rutas.external.clients;

import com.backend_tpi.ms_rutas.exceptions.BaseException;
import com.backend_tpi.ms_rutas.external.dtos.responses.CamionDTO;
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
                        .path("/camiones/disponible")
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
//  En caso de necesitar una api key, se puede configurar
//  en application-local.properties y usar acá
//  @Value("${api.ms-camiones.key}")
//  private String apiKey;
}
