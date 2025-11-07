package com.backend_tpi.ms_traslados.external.clients;

import com.backend_tpi.ms_traslados.external.dtos.responses.CiudadProvinciaDtoRes;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CamionesApiClient {

  private final RestClient camionesRestClient;

  public CamionesApiClient(@Qualifier("camionesRestClient") RestClient camionesRestClient) {
    this.camionesRestClient = camionesRestClient;
  }

//  En caso de necesitar una api key, se puede configurar
//  en application-local.properties y usar acá
//  @Value("${api.ms-camiones.key}")
//  private String apiKey;

  public CiudadProvinciaDtoRes getCiudadProvincia(Long idCiudad) {
    return camionesRestClient.get()
        .uri("/api/ciudades/{idCiudad}", idCiudad)
        .retrieve() // Acá es donde se ejecuta la petición
        .body(CiudadProvinciaDtoRes.class);
  }
}
