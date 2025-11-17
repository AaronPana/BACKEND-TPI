package com.backend_tpi.ms_traslados.external.clients;

import com.backend_tpi.ms_traslados.exceptions.BaseException;
import com.backend_tpi.ms_traslados.external.dtos.responses.CiudadProvinciaDtoRes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class  CamionesApiClient {

  private final RestClient camionesRestClient;

  public CamionesApiClient(@Qualifier("camionesRestClient") RestClient camionesRestClient) {
    this.camionesRestClient = camionesRestClient;
  }

//  En caso de necesitar una api key, se puede configurar
//  en application-local.properties y usar acá
//  @Value("${api.ms-camiones.key}")
//  private String apiKey;

  public CiudadProvinciaDtoRes getCiudadProvincia(Long idCiudad) {
    return this.camionesRestClient.get()
        .uri("/api/ciudades/{idCiudad}", idCiudad)
        .retrieve() // Acá es donde realmente se ejecuta la petición (IMPORTANTE)
        .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
          int status = response.getStatusCode().value();

          if (status == 404) {
            throw BaseException.notFoundById("Ciudad", idCiudad);
          }
          throw BaseException.badRequest("Error al obtener la ciudad");

        })
        .onStatus(HttpStatusCode::is5xxServerError, (request, response) -> {
          throw BaseException.internalError("Error al obtener la ciudad");
        })
        .body(CiudadProvinciaDtoRes.class);
  }
}
