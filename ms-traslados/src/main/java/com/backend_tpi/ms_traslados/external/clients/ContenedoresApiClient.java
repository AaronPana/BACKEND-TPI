package com.backend_tpi.ms_traslados.external.clients;

import com.backend_tpi.ms_traslados.exceptions.BaseException;
import com.backend_tpi.ms_traslados.external.dtos.requests.PesoVolumenDtoReq;
import com.backend_tpi.ms_traslados.external.dtos.responses.ContenedorDtoRes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ContenedoresApiClient {

  private final RestClient contenedoresRestClient;

  public ContenedoresApiClient(@Qualifier("contenedoresRestClient") RestClient contenedoresRestClient) {
    this.contenedoresRestClient = contenedoresRestClient;
  }

//  En caso de necesitar una api key, se puede configurar
//  en application-local.properties y usar acá
//  @Value("${api.ms-contenedores.key}")
//  private String apiKey;

  public ContenedorDtoRes postContenedor(PesoVolumenDtoReq pesoVolumenDTO) {
    return this.contenedoresRestClient.post()
        .uri("/contenedores")
        .contentType(MediaType.APPLICATION_JSON)
        .body(pesoVolumenDTO)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
          int status = response.getStatusCode().value();

          if (status == 400) {
            throw BaseException.badRequest("Datos inválidos para crear el contenedor");
          }
          throw BaseException.badRequest("Error al crear el contenedor");

        })
        .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
          throw BaseException.internalError("Error al crear el contenedor");
        })
        .body(ContenedorDtoRes.class);
  }

  public ContenedorDtoRes getContenedorById(Long idContenedor) {
    return this.contenedoresRestClient.get()
        .uri("/contenedores/{id}", idContenedor)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
          int status = response.getStatusCode().value();

          if (status == 404) {
            throw BaseException.notFoundById("Contenedor", idContenedor);
          }
          throw BaseException.badRequest("Error al obtener el contenedor");

        })
        .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
          throw BaseException.internalError("Error al obtener el contenedor");
        })
        .body(ContenedorDtoRes.class);
  }
}
