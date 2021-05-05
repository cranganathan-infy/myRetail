package com.assessment.myRetail.application.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class ApplicationConfig {

    @Bean
    RestTemplate restTemplate() {
        new RestTemplate()
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper()
    }
}
