package com.backend_tpi.ms_camiones.external.clients;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class TrasladosApiClient {

  private final RestClient trasladosRestClient;

  public TrasladosApiClient(@Qualifier("trasladosRestClient") RestClient trasladosRestClient) {
    this.trasladosRestClient = trasladosRestClient;
  }

//  En caso de necesitar una api key, se puede configurar
//  en application-local.properties y usar acá
//  @Value("${api.ms-traslados.key}")
//  private String apiKey;
}
