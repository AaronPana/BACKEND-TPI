package com.backend_tpi.ms_locations.external.clients;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;


@Component
public class ContenedoresApiClient {

  private final RestClient contenedoresRestClient;

  public ContenedoresApiClient(@Qualifier("contenedoresRestClient") RestClient contenedoresRestClient) {
    this.contenedoresRestClient = contenedoresRestClient;
  }

}
