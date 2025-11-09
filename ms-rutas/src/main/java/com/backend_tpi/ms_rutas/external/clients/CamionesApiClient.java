package com.backend_tpi.ms_rutas.external.clients;

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
}
