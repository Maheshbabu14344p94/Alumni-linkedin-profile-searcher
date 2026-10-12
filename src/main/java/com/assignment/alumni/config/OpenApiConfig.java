package com.assignment.alumni.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI alumniOpenApi() {
        return new OpenAPI().info(new Info()
            .title("Alumni LinkedIn Profile Searcher API")
            .version("1.0.0")
            .description("REST API for searching and retrieving alumni profiles persisted in PostgreSQL. PhantomBuster integration is enabled through environment configuration."));
    }
}
