package com.backend_tpi.ms_contenedores.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

  @Bean("camionesRestClient")
  RestClient camionesRestClient(@Value("${api.ms-camiones.base_url}") String baseUrl) {
    return RestClient.builder().baseUrl(baseUrl).build();
  }

  @Bean("rutasRestClient")
  RestClient rutasRestClient(@Value("${api.ms-rutas.base_url}") String baseUrl) {
    return RestClient.builder().baseUrl(baseUrl).build();
  }

  @Bean("trasladosRestClient")
  RestClient trasladosRestClient(@Value("${api.ms_traslados.base_url}") String baseUrl) {
    return RestClient.builder().baseUrl(baseUrl).build();
  }
}
