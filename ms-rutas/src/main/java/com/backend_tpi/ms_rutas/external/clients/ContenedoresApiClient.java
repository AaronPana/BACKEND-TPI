package com.backend_tpi.ms_rutas.external.clients;

import org.springframework.beans.factory.annotation.Qualifier;
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
}
