package com.mvabal.techtest.infraestructure.shared;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

  @Bean
  public RestClient restClient() {
    return RestClient.builder().baseUrl("http://localhost:3001/")
        .defaultHeader("Content-Type", "application/json")
        .defaultHeader("Accept", "application/json").build();
  }
}
