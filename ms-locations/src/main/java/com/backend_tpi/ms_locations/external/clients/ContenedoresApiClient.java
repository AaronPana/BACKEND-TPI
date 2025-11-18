package com.backend_tpi.ms_locations.external.clients;

import com.backend_tpi.ms_locations.exceptions.BaseException;
import com.backend_tpi.ms_locations.external.dtos.responses.DepositoAlternativoDtoRes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;


@Component
public class ContenedoresApiClient {

  private final RestClient contenedoresRestClient;

  public ContenedoresApiClient(@Qualifier("contenedoresRestClient") RestClient contenedoresRestClient) {
    this.contenedoresRestClient = contenedoresRestClient;
  }

  public List<DepositoAlternativoDtoRes> getAllDepositos() {
    return this.contenedoresRestClient.get()
        .uri("/depositos")
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
          throw BaseException.badRequest("Error al obtener los depositos");
        })
        .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
          throw BaseException.internalError("Error al obtener los depositos");
        })
        .body(new ParameterizedTypeReference<List<DepositoAlternativoDtoRes>>() {
        });
  }
}
