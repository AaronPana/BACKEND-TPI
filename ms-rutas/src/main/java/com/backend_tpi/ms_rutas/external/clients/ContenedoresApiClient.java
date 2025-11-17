package com.backend_tpi.ms_rutas.external.clients;

import com.backend_tpi.ms_rutas.exceptions.BaseException;
import com.backend_tpi.ms_rutas.external.dtos.responses.CostoEstadiaDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;

@Component
public class ContenedoresApiClient {

    private final RestClient contenedoresRestClient;

    public ContenedoresApiClient(@Qualifier("contenedoresRestClient") RestClient contenedoresRestClient) {
        this.contenedoresRestClient = contenedoresRestClient;
    }

    /*
    public CostoEstadiaDTO obtenerCostoEstadiaEstimada(Long idContenedor) {

        return contenedoresRestClient.get()
                .uri("/estadias/{id}/costo-estadia-estimada", idContenedor)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    throw BaseException.notFound("No se encontró costo estimado para estadia " + idContenedor);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    throw BaseException.internalError("Error al obtener costo estimado de estadía");
                })
                .body(CostoEstadiaDTO.class);
    }*/

    public void crearEstadia(Long idContenedor, Long idTraslado, Long idDeposito, LocalDateTime fechaInicio){
        contenedoresRestClient.post()
                .uri("/estadias/", idDeposito, idContenedor, idTraslado, fechaInicio)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    throw BaseException.notFound("No se encontró costo estimado para estadia " + idContenedor);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    throw BaseException.internalError("Error al obtener costo estimado de estadía");
                })
                .toBodilessEntity();;
    }


//  En caso de necesitar una api key, se puede configurar
//  en application-local.properties y usar acá
//  @Value("${api.ms-contenedores.key}")
//  private String apiKey;
}
