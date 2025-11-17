package com.backend_tpi.ms_locations.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

  @Bean("contenedoresRestClient")
  RestClient contenedoresRestClient(@Value("${api.ms-contenedores.base_url}") String baseUrl) {
    return RestClient.builder().baseUrl(baseUrl).build();
  }

  @Bean("osrmRestClient")
  RestClient osrmRestClient(@Value("${api.osrm.base_url}") String baseUrl) {
    return RestClient.builder().baseUrl(baseUrl).build();
  }
}
