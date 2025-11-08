package com.backend_tpi.ms_contenedores.external.clients;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class RutasApiClient {

  private final RestClient rutasRestClient;

  public RutasApiClient(@Qualifier("rutasRestClient") RestClient rutasRestClient) {
    this.rutasRestClient = rutasRestClient;
  }

//  En caso de necesitar una api key, se puede configurar
//  en application-local.properties y usar acá
//  @Value("${api.ms-rutas.key}")
//  private String apiKey;
}
