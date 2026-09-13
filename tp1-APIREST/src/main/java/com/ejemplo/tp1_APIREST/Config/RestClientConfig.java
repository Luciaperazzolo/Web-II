package com.ejemplo.tp1_APIREST.Config;

import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration; 
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Bean 
    public RestClient restClient() { 
        return RestClient.builder() 
        .baseUrl("https://dummyjson.com") 
        .build(); 
    }
}
